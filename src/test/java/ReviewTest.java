import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ReviewTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();


  @Test
  public void review_instantiatesCorrectly_true() {
    Review testReview = new Review(1, 89, "Good Movie");
    assertTrue(testReview instanceof Review);
  }

  @Test
  public void getMovieId_returnsCorrectMovieId_1() {
    Review testReview = new Review(1, 89, "Good Movie");
    assertEquals(1, testReview.getMovieId());
  }

  @Test
  public void getRating_returnsCorrectRating_89() {
    Review testReview = new Review(1, 89, "Good Movie");
    assertEquals(89, testReview.getRating());
  }

  @Test
  public void getReview_returnsCorrectReview_GoodMovie() {
    Review testReview = new Review(1, 89, "Good Movie");
    assertEquals("Good Movie", testReview.getReview());
  }

  @Test
  public void save_savesReviewIntoDatabase_true() {
    Review testReview = new Review(1, 89, "Good Movie");
    testReview.save();
    assertTrue(Review.all().get(0).equals(testReview));
  }

  @Test
  public void all_returnsAllReviewsInDB_true() {
    Review testReview1 = new Review(1, 89, "Good Movie");
    testReview1.save();
    Review testReview2 = new Review(1, 99, "Really Good Movie");
    testReview2.save();
    assertTrue(Review.all().get(0).equals(testReview1));
    assertTrue(Review.all().get(1).equals(testReview2));
  }

  @Test
  public void equals_ifReviewNameIsTheSame_true() {
    Review testReview1 = new Review(1, 89, "Good Movie");
    Review testReview2 = new Review(1, 89, "Good Movie");
    assertTrue(testReview1.equals(testReview2));
  }

  @Test
  public void save_assignsIdToReview_true() {
    Review testReview = new Review(1, 89, "Good Movie");
    testReview.save();
    assertEquals(testReview.getId(), Review.all().get(0).getId());
  }

  @Test
  public void getId_returnsAnId_true() {
    Review testReview = new Review(1, 89, "Good Movie");
    testReview.save();
    assertTrue(testReview.getId() > 0);
  }

  @Test
  public void find_returnsReviewWithSameId_testReview() {
    Review testReview = new Review(1, 89, "Good Movie");
    testReview.save();
    assertEquals(testReview, Review.find(testReview.getId()));
  }

  @Test
  public void getMovieTitle_returnsCorrectMovieName_fargo() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie.save();
    Review testReview = new Review(testMovie.getId(), 89, "Good Movie");
    testReview.save();
    assertEquals(testMovie.getTitle(), testReview.getMovieTitle());
  }

}
