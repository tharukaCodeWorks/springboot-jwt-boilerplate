package lk.teachmeit.boilerplate.service.interfaces;

import java.util.List;

public interface ICrudService<Input,Return> {
    Return create(Input input);
    Return update(Input input);
    boolean delete(Input input);
    boolean delete(long id);
    Return getById(long id);
    List<Return> getAll();
    List<Return> getPaginate(long page, long offset);
}
