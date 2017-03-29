import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.Arrays;


public class MovieTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void movie_instantiatesCorrectly_true() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    assertTrue(testMovie instanceof Movie);
  }

  @Test
  public void getTitle_returnsCorrectMovieTitle_Fargo() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    assertEquals("Fargo", testMovie.getTitle());
  }

  @Test
  public void getDirectorId_returnsCorrectDirectorId_1() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    assertEquals(1, testMovie.getDirectorId());
  }

  @Test
  public void getDescription_returnsCorrectMovieDescription_String() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    assertEquals("Jerry works in his father-in-law's car dealership", testMovie.getDescription());
  }

  @Test
  public void getGenre_returnsCorrectMovieGenre_Crime() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    assertEquals("Crime", testMovie.getGenre());
  }

  @Test
  public void getReleaseDate_returnsCorrectMovieReleaseDate_Timestamp() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    Timestamp testTimestamp = new Timestamp(Date.valueOf("1996-04-05").getTime());
    assertEquals(testTimestamp, testMovie.getReleaseDate());
  }

  @Test
  public void save_savesMovieIntoDatabase_true() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie.save();
    assertTrue(Movie.all().get(0).equals(testMovie));
  }

  @Test
  public void all_returnsAllMoviesInDB_true() {
    Movie testMovie1 = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie1.save();
    Movie testMovie2 = new Movie("True Grit", 1, "A tough U.S. Marshal helps a stubborn teenager track down her father's murderer.", "Western", "2010-12-22");
    testMovie2.save();
    assertTrue(Movie.all().get(0).equals(testMovie1));
    assertTrue(Movie.all().get(1).equals(testMovie2));
  }

  @Test
  public void equals_ifDirectorNameIsTheSame_true() {
    Movie testMovie1 = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    Movie testMovie2 = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    assertTrue(testMovie1.equals(testMovie2));
  }

  @Test
  public void save_assignsIdToMovie_true() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie.save();
    assertEquals(testMovie.getId(), Movie.all().get(0).getId());
  }

  @Test
  public void getId_returnsAnId_true() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie.save();
    assertTrue(testMovie.getId() > 0);
  }

  @Test
  public void find_returnsMovieWithSameId_Fargo() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie.save();
    assertEquals(testMovie, Movie.find(testMovie.getId()));
  }

  @Test
  public void getReviews_returnsAllReviewsWithCorrectMovieId_List() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie.save();
    Review testReview1 = new Review(testMovie.getId(), 89, "Good Movie");
    testReview1.save();
    Review testReview2 = new Review(testMovie.getId(), 99, "Really Good Movie");
    testReview2.save();
    Review[] reviews = new Review[] {testReview1, testReview2};
    assertTrue(testMovie.getReviews().containsAll(Arrays.asList(reviews)));
  }

}
