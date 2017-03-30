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
    // Timestamp testTimestamp = new Timestamp(Date.valueOf("1996-04-05").getTime());
    assertEquals("1996-04-05", testMovie.getReleaseDate());
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

  @Test
  public void getMovieRating_returnsAverageRatingForGivenMovie_float() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie.save();
    Review testReview1 = new Review(testMovie.getId(), 89, "Good Movie");
    testReview1.save();
    Review testReview2 = new Review(testMovie.getId(), 99, "Really Good Movie");
    testReview2.save();
    Review testReview3 = new Review(testMovie.getId(), 98, "Really Good Movie");
    testReview3.save();
    assertEquals(95.3f, testMovie.getMovieRating(), 0.1);
  }

  @Test
  public void getTopMovies_returnsHighestRatedMovies_list() {
    Movie testMovie1 = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie1.save();
    Movie testMovie2 = new Movie("Fantasic Mr.Fox", 1, "A tough U.S. Marshal helps a stubborn teenager track down her father's murderer.", "Western", "2010-12-22");
    testMovie2.save();
    Review testReview1 = new Review(testMovie1.getId(), 89, "Good Movie");
    testReview1.save();
    Review testReview2 = new Review(testMovie2.getId(), 99, "Really Good Movie");
    testReview2.save();
    assertTrue(Movie.getTopMovies().get(0).equals(testMovie2));
    assertTrue(Movie.getTopMovies().get(1).equals(testMovie1));
  }

  @Test
  public void updateMovie_updatesMovieProperties_true() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie.save();
    testMovie.updateMovie("Fantasic Mr.Fox", 2, "A tough U.S. Marshal helps a stubborn teenager track down her father's murderer.", "Western", "2010-12-22");
    // Timestamp testTimestamp = new Timestamp(Date.valueOf("2010-12-22").getTime());
    assertEquals("Fantasic Mr.Fox", Movie.find(testMovie.getId()).getTitle());
    assertEquals("Fantasic Mr.Fox", testMovie.getTitle());
    assertEquals(2, Movie.find(testMovie.getId()).getDirectorId());
    assertEquals(2, testMovie.getDirectorId());
    assertEquals("A tough U.S. Marshal helps a stubborn teenager track down her father's murderer.", Movie.find(testMovie.getId()).getDescription());
    assertEquals("A tough U.S. Marshal helps a stubborn teenager track down her father's murderer.", testMovie.getDescription());
    assertEquals("Western", Movie.find(testMovie.getId()).getGenre());
    assertEquals("Western", testMovie.getGenre());
    assertEquals("2010-12-22", Movie.find(testMovie.getId()).getReleaseDate());
    assertEquals("2010-12-22", testMovie.getReleaseDate());
  }

  @Test
  public void deleteMovie_deletesMovieFromDB_true() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie.save();
    Review testReview1 = new Review(testMovie.getId(), 89, "Good Movie");
    testReview1.save();
    Review testReview2 = new Review(testMovie.getId(), 99, "Really Good Movie");
    testReview2.save();
    int testMovieId = testMovie.getId();
    int testReview1Id = testReview1.getId();
    int testReview2Id = testReview2.getId();
    testMovie.deleteMovie();
    assertEquals(null, Movie.find(testMovieId));
    assertEquals(null, Review.find(testReview1Id));
    assertEquals(null, Review.find(testReview2Id));
  }

  @Test
  public void searchMovie_returnsAllMoviesWithMatchingString_List() {
    Movie testMovie1 = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie1.save();
    Movie testMovie2 = new Movie("Fantasic Mr.Fox", 1, "A tough U.S. Marshal helps a stubborn teenager track down her father's murderer.", "Western", "2010-12-22");
    testMovie2.save();
    Movie testMovie3 = new Movie("The Big Lebowski", 1, "The Dude Lebowski, mistaken for a millionaire Lebowski, seeks restitution for his ruined rug and enlists his bowling buddies to help get it.", "Comedy", "1998-03-06");
    testMovie3.save();
    Movie[] movies = new Movie[] {testMovie1, testMovie2};
    assertTrue(Movie.searchMovie("fa").containsAll(Arrays.asList(movies)));
    assertFalse(Movie.searchMovie("fa").contains(testMovie3));
  }

  @Test
  public void getMovieReviewCount_returnsNumberOfReviewsForGivenMovie_3() {
    Movie testMovie = new Movie("Fargo", 1, "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie.save();
    Review testReview1 = new Review(testMovie.getId(), 89, "Good Movie");
    testReview1.save();
    Review testReview2 = new Review(testMovie.getId(), 99, "Really Good Movie");
    testReview2.save();
    Review testReview3 = new Review(testMovie.getId(), 97, "pretty Good Movie");
    testReview3.save();
    assertEquals(Integer.valueOf(3), testMovie.getMovieReviewCount());
  }

}
