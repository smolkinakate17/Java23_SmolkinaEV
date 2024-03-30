package org.example.model.statistic.manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.model.entities.PaymentMethod;
import org.example.model.exceptions.ValueAlreadyExistException;
import org.example.model.exceptions.ValueNotExistException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentMethodManager {
    private final EntityManager entityManager;

    public Optional<PaymentMethod> find(PaymentMethod paymentMethod){
        PaymentMethod m=entityManager.find(PaymentMethod.class,paymentMethod.getId());
        if(m==null){
            return Optional.empty();
        }
        return Optional.of(m);
    }
    public Optional<PaymentMethod> find(long id){
        PaymentMethod m=entityManager.find(PaymentMethod.class,id);
        if(m==null){
            return Optional.empty();
        }
        return Optional.of(m);
    }
    public List<PaymentMethod> findAll(){
        return entityManager.createQuery("from PaymentMethod m", PaymentMethod.class).getResultList();
    }
    public Optional<PaymentMethod> find(String title){
        try {
            return Optional.of(entityManager.createQuery("select m from PaymentMethod m where m.title=:title", PaymentMethod.class)
                    .setParameter("title",title).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    public PaymentMethod add(PaymentMethod paymentMethod) throws ValueAlreadyExistException {
        Optional<PaymentMethod> find=find(paymentMethod);
        if(find.isPresent()){
            throw new ValueAlreadyExistException(paymentMethod);
        }
        var transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(paymentMethod);
            transaction.commit();
            return paymentMethod;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
    public PaymentMethod update(PaymentMethod paymentMethod) throws ValueNotExistException{
        Optional<PaymentMethod> find=find(paymentMethod);
        if(find.isEmpty()){
            throw new ValueNotExistException(paymentMethod);
        }
        var transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(paymentMethod);
        transaction.commit();
        return paymentMethod;
    }
    public PaymentMethod delete(PaymentMethod paymentMethod)throws ValueNotExistException{
        Optional<PaymentMethod> find=find(paymentMethod);
        if(find.isEmpty()){
            throw new ValueNotExistException(paymentMethod);
        }
        var transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(paymentMethod);
        transaction.commit();
        return paymentMethod;
    }
    public PaymentMethod delete(long id){
        Optional<PaymentMethod> find=find(id);
        if(find.isEmpty()){
            throw new ValueNotExistException(id);
        }
        var transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(find.get());
        transaction.commit();
        return find.get();
    }
}
