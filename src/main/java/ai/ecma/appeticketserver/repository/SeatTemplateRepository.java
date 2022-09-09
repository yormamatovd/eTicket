package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.SeatTemplate;
import ai.ecma.appeticketserver.entity.TemplateChair;
import ai.ecma.appeticketserver.payload.resp.SectorResDto;
import ai.ecma.appeticketserver.payload.resp.TemplateResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository()
public interface SeatTemplateRepository extends JpaRepository<SeatTemplate, UUID> {
    @Query(value = "select cast(st.id as varchar) as id, st.name, count(tc.*) as countChairs,\n" +
            "       (select max(price) from template_chairs stc where stc.template_id = st.id) as maxPrice,\n" +
            "       (select min(price) from template_chairs stc where stc.template_id = st.id) as minPrice\n" +
            "from seat_templates st\n" +
            "         join template_chairs tc on st.id = tc.template_id group by st.id, st.name;",nativeQuery = true)
    List<TemplateResDto> allByMaxMin();

    @Query(value = "select tc.sector, (select count(*) from template_chairs stc where stc.template_id = :templateId and stc.sector=tc.sector) as chairCount\n" +
            "from template_chairs tc\n" +
            "where template_id = :templateId group by tc.sector;", nativeQuery = true)
    List<SectorResDto> getAllSectors(@Param("templateId") UUID templateId);
}
