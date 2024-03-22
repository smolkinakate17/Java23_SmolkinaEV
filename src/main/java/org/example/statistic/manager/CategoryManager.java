package org.example.statistic.manager;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.entities.Category;
import org.example.exceptions.ValueAlreadyExistException;
import org.example.exceptions.ValueNotExistException;

import java.util.Optional;

@RequiredArgsConstructor
public class CategoryManager {
    private final EntityManager entityManager;

    public Optional<Category> find(Category category){
        Category c=entityManager.find(Category.class,category.getId());
        if(c==null){
            return Optional.empty();
        }
        return Optional.of(c);
    }
    public Optional<Category>find(long id){
        Category c=entityManager.find(Category.class,id);
        if(c==null){
            return Optional.empty();
        }
        return Optional.of(c);
    }
    public Category add(Category category) throws ValueAlreadyExistException {
        Optional<Category> find=find(category);
        if(find.isPresent()){
            throw new ValueAlreadyExistException(category);
        }
        var transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(category);
            transaction.commit();
            return category;

        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
    public Category update(Category category)throws ValueNotExistException{
        Optional<Category> find=find(category);
        if(find.isEmpty()){
            throw new ValueNotExistException(category);
        }
        var transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(category);
        transaction.commit();
        return category;
    }
    public Category delete(Category category)throws ValueNotExistException{
        Optional<Category> find=find(category);
        if(find.isEmpty()){
            throw new ValueNotExistException(category);
        }
        var transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(category);
        transaction.commit();
        return category;
    }
    public Category delete(long id){
        Optional<Category> find=find(id);
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
