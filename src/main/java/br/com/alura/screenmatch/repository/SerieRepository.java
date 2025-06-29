package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);

    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int totalTemporadas, double avaliacao);

    @Query("""
            SELECT s
            FROM Serie s
            WHERE s.totalTemporadas <= :totalTemporadas
            AND s.avaliacao >= :avaliacao
            """
    )
    List<Serie> seriesPorTemporadaEAvaliacao(@Param("totalTemporadas") int totalTemporadas, @Param("avaliacao") double avaliacao);

    @Query("""
            SELECT e
            FROM Serie s
            JOIN s.episodios e
            WHERE e.titulo ILIKE %:trechoEpisodio%
            """)
    List<Episodio> episodiosPorTrecho(@Param("trechoEpisodio") String trechoEpisodio);

    @Query("""
            SELECT e
            FROM Serie s
            JOIN s.episodios e
            WHERE s = :serie
            ORDER BY e.avaliacao DESC
            LIMIT 5
            """)
    List<Episodio> topEpisodiosPorSerie(@Param("serie") Serie serie);

    @Query("""
            SELECT e
            FROM Serie s
            JOIN s.episodios e
            WHERE s = :serie
            AND YEAR(e.dataLancamento) >= :anoLancamento
            """)
    List<Episodio> episodioPorSerieEAno(@Param("serie") Serie serie, @Param("anoLancamento") int anoLancamento);
}
