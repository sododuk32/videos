package goldstarproject.template.community.board.repository;

import goldstarproject.template.community.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>,BoardRepositoryCustom{

    Optional<Board> findById(Long id);
}
