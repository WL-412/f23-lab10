package AndrewWebServices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.junit.Before;
import org.junit.Test;

public class AndrewWebServicesTest {
    InMemoryDatabase fakeDatabase;
    RecSys recommender;
    PromoService promoService;
    AndrewWebServices andrewWebService;

    @Before
    public void setUp() {
        // You need to use some mock objects here
        MockitoAnnotations.initMocks(this);
        fakeDatabase = new InMemoryDatabase(); // We probably don't want to access our real database...
        fakeDatabase.addAccount("user1", 1234);
        fakeDatabase.addAccount("user2", 5678);

        // recommender = new RecSys();
        recommender = mock(RecSys.class);
        when(recommender.getRecommendation("user1")).thenReturn("hi");
        when(recommender.getRecommendation("user2")).thenReturn("hihi");

        promoService = mock(PromoService.class);

        andrewWebService = new AndrewWebServices(fakeDatabase, recommender, promoService);
    }

    @Test
    public void testLogIn() {
        // This is taking way too long to test
        // assertTrue(andrewWebService.logIn("Scotty", 17214));
        assertTrue(andrewWebService.logIn("user1", 1234));
        assertFalse(andrewWebService.logIn("user1", 0000));

    }

    @Test
    public void testGetRecommendation() {
        // This is taking way too long to test
        assertEquals("hi", andrewWebService.getRecommendation("user1"));
        assertEquals("hihi", andrewWebService.getRecommendation("user2"));
    }

    @Test
    public void testSendEmail() {
        // How should we test sendEmail() when it doesn't have a return value?
        // Hint: is there something from Mockito that seems useful here?
        andrewWebService.sendPromoEmail("test@example.com");
        verify(promoService).mailTo("test@example.com");
    }

    @Test
    public void testNoSendEmail() {
        // How should we test that no email has been sent in certain situations (like right after logging in)?
        // Hint: is there something from Mockito that seems useful here?
        andrewWebService.logIn("user1", 1234);
        verify(promoService, never()).mailTo(anyString());
    }
}
