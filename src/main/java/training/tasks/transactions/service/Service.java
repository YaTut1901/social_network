package training.tasks.transactions.service;

import java.util.List;

public interface Service<M> {
    M getOne(Long id);
    List<M> getAll();
    M save(M model);
    List<M> addAll(List<M> models);
    void delete(Long id);
    boolean exists(Long id);
}
