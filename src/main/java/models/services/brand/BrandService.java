package models.services.brand;

import models.entities.Brand;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.FileUtil;
import utils.HibernateUtils;
import view_models.brands.BrandCreateRequest;
import view_models.brands.BrandGetPagingRequest;
import view_models.brands.BrandUpdateRequest;
import view_models.brands.BrandViewModel;

import java.util.ArrayList;
import java.util.List;

public class BrandService implements  IBrandService{
    private static BrandService instance = null;

    public static BrandService getInstance(){
        if (instance == null)
            instance = new BrandService();
        return instance;
    }

    @Override
    public int insert(BrandCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        Brand brand = new Brand();

        brand.setBrandName(request.getBrandName());
        brand.setOrigin(request.getOrigin());
        brand.setImage(FileUtil.encodeBase64(request.getImage()));

        int brandId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(brand);
            brandId = brand.getBrandId();

            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return brandId;
    }

    @Override
    public boolean update(BrandUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        Brand brand = session.find(Brand.class, request.getBrandId());

        brand.setBrandName(request.getBrandName());
        brand.setOrigin(request.getOrigin());
        if(request.getImage() != null){
            brand.setImage(FileUtil.encodeBase64(request.getImage()));
        }


        return HibernateUtils.merge(brand);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Brand brand = session.find(Brand.class, entityId);
        String cmd = "update Product set brandId = null where brandId =:s1";
        Query q = session.createQuery(cmd);
        q.setParameter("s1", brand.getBrandId());
        q.executeUpdate();
        return HibernateUtils.remove(brand);
    }
    private BrandViewModel getBrandViewModel(Brand brand){
        BrandViewModel brandViewModel = new BrandViewModel();

        brandViewModel.setBrandId(brand.getBrandId());
        brandViewModel.setBrandName(brand.getBrandName());
        brandViewModel.setOrigin(brand.getOrigin());
        brandViewModel.setImage(brand.getImage());

        return brandViewModel;
    }
    @Override
    public BrandViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Brand brand = session.find(Brand.class, entityId);

        BrandViewModel brandViewModel = getBrandViewModel(brand);
        session.close();

        return brandViewModel;
    }

    @Override
    public ArrayList<BrandViewModel> retrieveAll(BrandGetPagingRequest request) {
        ArrayList<BrandViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("Brand", request.getColumnName(), request.getKeyword(), request.getTypeSort());
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<Brand> brands = q.list();

        for(Brand brand:brands){
            BrandViewModel v = getBrandViewModel(brand);
            list.add(v);
        }
        session.close();
        return list;
    }
}
