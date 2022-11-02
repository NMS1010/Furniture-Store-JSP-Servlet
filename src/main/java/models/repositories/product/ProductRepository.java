package models.repositories.product;

import models.entities.Product;
import models.entities.ProductImage;
import models.services.product.ProductService;
import models.services.product_images.ProductImageService;
import models.services.review_item.ReviewItemService;
import models.view_models.product_images.ProductImageViewModel;
import models.view_models.products.ProductCreateRequest;
import models.view_models.products.ProductGetPagingRequest;
import models.view_models.products.ProductUpdateRequest;
import models.view_models.products.ProductViewModel;
import models.view_models.review_items.ReviewItemViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.FileUtil;
import utils.HibernateUtils;
import utils.HtmlClassUtils;
import utils.constants.PRODUCT_STATUS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements IProductRepository{
    private static ProductRepository instance = null;
    public static ProductRepository getInstance() {
        if(instance == null)
            instance = new ProductRepository();
        return instance;
    }
    @Override
    public int insert(ProductCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        Product product = new Product();
        if(request.getStatus() == PRODUCT_STATUS.OUT_STOCK || request.getStatus() == PRODUCT_STATUS.SUSPENDED){
            request.setQuantity(0);
        }
        if(request.getQuantity() == 0){
            request.setStatus(PRODUCT_STATUS.OUT_STOCK);
        }
        product.setName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setOrigin(request.getOrigin());
        product.setDateCreated(DateUtils.dateTimeNow());
        product.setStatus(request.getStatus());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setCategoryId(request.getCategoryId());
        product.setBrandId(request.getBrandId());

        int productId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(product);
            productId = product.getProductId();
            if(productId == -1){
                return -1;
            }
            if(request.getImage() != null && !request.getImage().getSubmittedFileName().equals("")){
                ProductImage img = new ProductImage();
                img.setDefault(true);
                img.setProductId(productId);
                img.setImage(FileUtil.encodeBase64(request.getImage()));
                session.persist(img);
            }
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        session.close();
        return productId;
    }

    @Override
    public boolean update(ProductUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        Product product = session.find(Product.class, request.getProductId());
        if(request.getStatus() == PRODUCT_STATUS.OUT_STOCK || request.getStatus() == PRODUCT_STATUS.SUSPENDED){
            request.setQuantity(0);
        }
        if(request.getQuantity() == 0){
            request.setStatus(PRODUCT_STATUS.OUT_STOCK);
        }
        //BigDecimal prevPrice = product.getPrice();
        product.setName(request.getProductName());
        product.setOrigin(request.getOrigin());
        product.setDescription(request.getDescription());
        product.setStatus(request.getStatus());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        if(request.getCategoryId() > 0)
            product.setCategoryId(request.getCategoryId());
        if(request.getBrandId() > 0)
            product.setBrandId(request.getBrandId());

        try {
            tx = session.beginTransaction();
            session.merge(product);

            if(request.getImage() != null && !request.getImage().getSubmittedFileName().equals("")){

                Query q = session.createQuery("from ProductImage where productId=:s1 and isDefault = true");
                q.setParameter("s1", product.getProductId());

                ProductImage image = (ProductImage)q.getSingleResult();
                image.setImage(FileUtil.encodeBase64(request.getImage()));

                session.merge(image);
            }
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Product product = session.find(Product.class, entityId);
        product.setStatus(PRODUCT_STATUS.SUSPENDED);
        session.close();
        return HibernateUtils.merge(product);
    }
    private String getStatus(int i){
        String status = "";
        switch (i){
            case PRODUCT_STATUS.IN_STOCK:
                status = "In Stock";
                break;
            case PRODUCT_STATUS.OUT_STOCK:
                status = "Out Stock";
                break;
            case PRODUCT_STATUS.SUSPENDED:
                status = "Suspended";
                break;
            default:
                status = "Undefined";
                break;
        }
        return status;
    }
    private ProductViewModel getProductViewModel(Product product, Session session){
        Query q1 = session.createQuery("select image from ProductImage where productId =:s1 and isDefault = true");
        q1.setParameter("s1", product.getProductId());
        String image = q1.getSingleResult().toString();
        Query q2 = session.createQuery("select brandName from Brand where brandId =:s1" );
        q2.setParameter("s1", product.getBrandId());
        String brandName = q2.getSingleResult().toString();
        Query q3 = session.createQuery("select categoryName from Category where categoryId =:s1" );
        q3.setParameter("s1", product.getCategoryId());
        String categoryName = q3.getSingleResult().toString();
        Query q4 = session.createQuery("select productImageId from ProductImage where  productId=:s1 and isDefault = false");
        q4.setParameter("s1", product.getProductId());
        List<Integer> subProductImageIds = q4.list();
        Query q5 = session.createQuery("select sum(quantity) from OrderItem  where productId=:s1");
        q5.setParameter("s1",product.getProductId());
        Object res = q5.getSingleResult();
        long totalPurchased = res == null ? 0 : (long)res;

        ProductViewModel productViewModel = new ProductViewModel();

        productViewModel.setProductId(product.getProductId());
        productViewModel.setName(product.getName());
        productViewModel.setDescription(product.getDescription());
        productViewModel.setOrigin(product.getOrigin());
        productViewModel.setDateCreated(DateUtils.dateTimeToStringWithFormat(product.getDateCreated(),"yyyy-MM-dd HH:mm:ss"));
        productViewModel.setStatus(product.getStatus());
        productViewModel.setStatusCode(getStatus(product.getStatus()));
        productViewModel.setPrice(product.getPrice());
        productViewModel.setQuantity(product.getQuantity());
        productViewModel.setImage(image);
        productViewModel.setBrandName(brandName);
        productViewModel.setCategoryName(categoryName);
        productViewModel.setCategoryId(product.getCategoryId());
        productViewModel.setBrandId(product.getBrandId());
        productViewModel.setTotalPurchased(totalPurchased);
        productViewModel.setStatusClass(HtmlClassUtils.generateProductStatusClass(product.getStatus()));
        List<ProductImageViewModel> productImageViewModels = new ArrayList<>();
        subProductImageIds.forEach(id -> {
            productImageViewModels.add(ProductImageService.getInstance().retrieveProductImageById(id));
        });
        productViewModel.setProductImages(productImageViewModels);
        ArrayList<ReviewItemViewModel> productReviews = ReviewItemService.getInstance().retrieveReviewItemByProductId(product.getProductId());
        int totalRating = productReviews.stream().mapToInt(ReviewItemViewModel::getRating).sum();
        long avgRating = Math.round((totalRating * 1.0)/productReviews.size());

        productViewModel.setProductReviews(productReviews);
        productViewModel.setAvgRating(avgRating);
        return productViewModel;
    }
    @Override
    public ProductViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Product product = session.find(Product.class, entityId);

        ProductViewModel productViewModel = getProductViewModel(product, session);
        session.close();

        return productViewModel;
    }


    @Override
    public ArrayList<ProductViewModel> retrieveAll(ProductGetPagingRequest request) {
        ArrayList<ProductViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("Product", request.getColumnName(),request.getSortBy(), request.getKeyword(), request.getTypeSort());
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<Product> products = q.list();

        for(Product product:products){
            ProductViewModel v = getProductViewModel(product, session);
            list.add(v);
        }
        session.close();
        return list;
    }

    @Override
    public boolean updateQuantity(int productId, int quantity) {
        Session session = HibernateUtils.getSession();
        Product product = session.find(Product.class, productId);
        product.setQuantity(product.getQuantity() - quantity);
        session.close();
        return HibernateUtils.merge(product);
    }
}
