package goldstarproject.template.common.generic;

import org.springframework.data.domain.Page;

import java.util.List;

public interface GenericMapper<D, E> {
    E toEntity(D d);
    D toDto (E e);
    List<D> toDto (List<E> entityList);
    List<E> toEntity(List<D> dtoList);
}
