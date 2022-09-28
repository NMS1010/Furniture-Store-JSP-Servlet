package models.services.product_images;

import models.entities.ProductImages;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.FileUtil;
import utils.HibernateUtils;
import view_models.product_images.ProductImageCreateRequest;
import view_models.product_images.ProductImageGetPagingRequest;
import view_models.product_images.ProductImageUpdateRequest;
import view_models.product_images.ProductImageViewModel;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

public class ProductImageService implements  IProductImageService{
    private static ProductImageService instance = null;
    public ProductImageService(){

    }
    public static ProductImageService getInstance(){
        if(instance == null){
            instance = new ProductImageService();
        }
        return instance;
    }
    @Override
    public int insert(ProductImageCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        List<Part> files = request.getImages();
        try {
            tx = session.beginTransaction();
            for(Part f: files){
                ProductImages productImages = new ProductImages();

                productImages.setProductId(request.getProductId());
                productImages.setDefault(false);
                productImages.setImage(FileUtil.encodeBase64(f));

                session.persist(productImages);
            }
            tx.commit();
        }
        catch (Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
            session.close();
            return -1;
        }
        return 1;
    }

    @Override
    public boolean update(ProductImageUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        ProductImages img = session.find(ProductImages.class, request.productImageId);

        img.setImage(FileUtil.encodeBase64(request.getImage()));
        return HibernateUtils.merge(img);

    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        ProductImages img = session.find(ProductImages.class, entityId);
        return HibernateUtils.remove(img);
    }
    private ProductImageViewModel getProductImageViewModel(ProductImages productImages){
        ProductImageViewModel productImageViewModel = new ProductImageViewModel();

        productImageViewModel.setId(productImages.getId());
        productImageViewModel.setDefault(productImages.getDefault());
        productImageViewModel.setImage(productImages.getImage());
        productImageViewModel.setProductId(productImages.getProductId());

        return productImageViewModel;
    }
    @Override
    public ProductImageViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        ProductImages productImages = session.find(ProductImages.class, entityId);

        ProductImageViewModel productImageViewModel = getProductImageViewModel(productImages);
        session.close();
        return productImageViewModel;
    }

    @Override
    public ArrayList<ProductImageViewModel> retrieveAll(ProductImageGetPagingRequest request) {
        ArrayList<ProductImageViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();

        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("ProductImages", request.getColumnName(), request.getKeyword(), request.getTypeSort());

        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<ProductImages> productImages = q.list();

        for(ProductImages productImg:productImages){
            ProductImageViewModel v = getProductImageViewModel(productImg);
            list.add(v);
        }
        session.close();
        return list;
    }
}
