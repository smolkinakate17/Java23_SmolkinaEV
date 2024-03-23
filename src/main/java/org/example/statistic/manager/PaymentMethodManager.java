package org.example.statistic.manager;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.entities.PaymentMethod;
import org.example.exceptions.ValueAlreadyExistException;
import org.example.exceptions.ValueNotExistException;

import java.util.Optional;

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
