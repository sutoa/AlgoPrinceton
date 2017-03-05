import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class PercolationTest {
    private Percolation p;

    @Before
    public void setUp() throws Exception {
        p = new Percolation(10);
    }

    @Test
    public void topOpenSiteIsAlwaysFull() throws Exception {
        p.open(1,1);
        assertTrue(p.isFull(1,1));
    }

    @Test
    public void openSite() throws Exception {
        p.open(1,1);
        assertTrue(p.isOpen(1,1));
    }

    @Test
    public void openSiteConnectedToTopOpenSiteIsFull() throws Exception {
        p.open(1, 4);
        p.open(2, 4);
        p.open(2, 9);

        assertTrue(p.isOpen(1, 4));
        assertTrue(p.isOpen(2, 4));
        assertTrue(p.isOpen(2, 9));

        assertTrue(p.isFull(1, 4));
        assertTrue(p.isFull(2, 4));
        assertFalse(p.isFull(2, 9));
    }

    @Test
    public void isFullWhenConnectedToATopSiteVerticallyOrHorizontally() throws Exception {
        p.open(1, 4);
        p.open(1, 5);
        p.open(2, 5);

        assertTrue(p.isFull(2, 5));
    }

    @Test
    public void isNotFullWhenConnectedToATopSiteDiagnoally() throws Exception {
        p.open(1, 4);
        p.open(2, 5);

        assertFalse(p.isFull(2, 5));
    }

    @Test
    public void isNotFullWhenNotConnectedToATopSite() throws Exception {
        p.open(1, 4);
        p.open(2, 6);

        assertFalse(p.isFull(2, 6));
    }

    @Test
    public void percolatesWhenTheresAnOpenPathFromTopToBottm() throws Exception {
        p.open(1,3);
        p.open(2,3);
        p.open(3,3);
        p.open(4,3);
        p.open(5,3);
        p.open(6,3);
        p.open(7,3);
        p.open(8,3);
        p.open(8,4);
        p.open(9,4);
        p.open(10,4);

        assertTrue(p.percolates());
    }

    @Test
    public void doesNotPercolateWhenTheresNoOpenPathFromTopToBottm() throws Exception {
        p.open(1,3);
        p.open(2,3);
        p.open(3,3);
        p.open(4,3);
        p.open(5,3);
        p.open(6,3);
        p.open(7,3);
        p.open(8,3);
        p.open(8,4);
        p.open(9,5);
        p.open(10,6);

        assertFalse(p.percolates());
    }
}
