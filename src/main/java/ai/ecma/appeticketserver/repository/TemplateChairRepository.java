package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.TemplateChair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TemplateChairRepository extends JpaRepository<TemplateChair, UUID> {
    List<TemplateChair> findAllByTemplateId(UUID template_id);

    List<TemplateChair> findAllBySectorAndTemplateIdOrderByRow(String sector, UUID templateId);

    boolean existsBySectorAndTemplateId(String sector, UUID template_id);

    boolean existsBySectorAndRowAndTemplateId(String sector, String row, UUID template_id);

    boolean existsBySectorAndTemplateIdAndRow(String sector, UUID template_id, String row);

    @Query(value = "select max(vt.m) from(select max(tc.row) as m\n" +
            "from template_chairs tc\n" +
            "where template_id = :templateId\n" +
            "  and sector = :sector\n" +
            "group by tc.row) as vt;", nativeQuery = true)
    long countRowByTemplateIdAndSector(@Param("templateId") UUID templateId, @Param("sector") String sector);

    @Query(value = "select max(vt.m)\n" +
            "from (select max(cast(tc.name as integer)) as m\n" +
            "      from template_chairs tc\n" +
            "      where template_id = :templateId\n" +
            "        and sector = :sector\n" +
            "        and row = :row\n" +
            "      group by tc.name) as vt", nativeQuery = true)
    long getMaxChairByTemplateAndSectionAndRow(@Param("templateId") UUID templateId, @Param("sector") String sector, @Param("row") String row);
}
