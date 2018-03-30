/**
 *
 */
package com.manager.store.mapper;

import com.manager.store.entities.Product;
import com.manager.store.entities.Staff;
import com.manager.store.entities.Store;
import com.manager.store.entities.Transaction;
import com.manager.store.model.ModelSummary;
import com.manager.store.model.TransactionModel;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import static com.manager.store.utils.DateUtils.fromDateTime;
import static com.manager.store.utils.DateUtils.toDateTime;

/**
 * @author Marek
 */
@Component
public class TransactionMapper implements Mapper<Transaction, TransactionModel> {

    @Override
    public Transaction toEntity(TransactionModel model, Transaction existingEntity, SessionFactory sessionFactory) {
        Transaction ent = existingEntity == null ? new Transaction() : existingEntity;

        ent.setId(model.getId());
        ent.setDate(toDateTime(model.getDate()));

        Product product = (Product) sessionFactory
                .getCurrentSession()
                .get(Product.class, model.getProduct().getId());
        ent.setProduct(product);

        Store store = (Store) sessionFactory
                .getCurrentSession()
                .get(Store.class, model.getStore().getId());
        ent.setStore(store);

        Staff staff = (Staff) sessionFactory
                .getCurrentSession()
                .get(Staff.class, model.getStaff().getId());
        ent.setStaff(staff);

        ent.setQuantity(model.getQuantity());

        return ent;
    }

    @Override
    public TransactionModel toModel(Transaction entity, SessionFactory sessionFactory) {
        TransactionModel mod = new TransactionModel();

        mod.setId(entity.getId());
        mod.setDate(fromDateTime(entity.getDate()));
        mod.setQuantity(entity.getQuantity());

        Product product = entity.getProduct();
        ModelSummary productModel = new ModelSummary(
                product.getId(),
                product.getName(),
                product.getModel() + " - " + product.getProducer());
        mod.setProduct(productModel);

        Store store = entity.getStore();
        ModelSummary storeModel = new ModelSummary(store.getId(), store.getName(), store.getAddress());
        mod.setStore(storeModel);

        Staff staff = entity.getStaff();
        ModelSummary staffModel = new ModelSummary(staff.getId(), staff.getFirstName() + " " + staff.getLastName(), "");
        mod.setStaff(staffModel);

        return mod;
    }

    @Override
    public ModelSummary summaryFromEntity(final Transaction entity) {
        return new ModelSummary(entity.getId(),
                fromDateTime(entity.getDate()),
                entity.getProduct().getName() + " x " + entity.getQuantity());
    }

    @Override
    public ModelSummary summaryFromModel(final TransactionModel model) {
        return new ModelSummary(model.getId(),
                model.getDate(),
                model.getProduct().getName() + " + " + model.getQuantity());
    }
}
