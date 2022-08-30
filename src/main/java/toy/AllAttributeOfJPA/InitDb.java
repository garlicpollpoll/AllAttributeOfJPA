package toy.AllAttributeOfJPA;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toy.AllAttributeOfJPA.entity.Address;
import toy.AllAttributeOfJPA.entity.Item;
import toy.AllAttributeOfJPA.entity.Member;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void dbInit() {
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {

        private final EntityManager em;

        public void init() {
            Item item1 = new Item("JPA", 5000, 100, "김영한", "책");
            Item item2 = new Item("토비의Spring", 5000, 100, "김영한", "책");
            Item item3 = new Item("아웃라이어", 5000, 100, "김영한", "책");
            Item item4 = new Item("인터스텔라의과학", 5000, 100, "김영한", "책");
            Item item5 = new Item("박문각편입", 10000, 100, "김영한", "책");
            Item item6 = new Item("지구라는행성", 10000, 100, "김영한", "책");
            Item item7 = new Item("평행우주", 10000, 100, "김영한", "책");
            Item item8 = new Item("코스모스", 10000, 100, "김영한", "책");
            Item item9 = new Item("차세대웹프로그래밍", 15000, 100, "김영한", "책");
            Item item10 = new Item("수중기사", 15000, 100, "김영한", "책");
            Item item11 = new Item("수기모형", 15000, 100, "김영한", "책");
            Item item12 = new Item("괴짜심리학", 15000, 100, "김영한", "책");
            Item item13 = new Item("역사", 20000, 100, "김영한", "책");
            Item item14 = new Item("객체지향의사실과오해", 20000, 100, "김영한", "책");
            Item item15 = new Item("안드로이드프로그래밍", 20000, 100, "김영한", "책");
            Item item16 = new Item("C++프로그래밍", 25000, 100, "김영한", "책");

            Member member = new Member("경석", 24, "ks3254", "ks32541007!", new Address("서울", "월드컵로", "1111"));
            em.persist(member);

            em.persist(item1);
            em.persist(item2);
            em.persist(item3);
            em.persist(item4);
            em.persist(item5);
            em.persist(item6);
            em.persist(item7);
            em.persist(item8);
            em.persist(item9);
            em.persist(item10);
            em.persist(item11);
            em.persist(item12);
            em.persist(item13);
            em.persist(item14);
            em.persist(item15);
            em.persist(item16);
        }
    }
}
