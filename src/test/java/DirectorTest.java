import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class DirectorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void director_instantiatesCorrectly_true() {
    Director testDirector = new Director("Coen Brothers");
    assertTrue(testDirector instanceof Director);
  }

  @Test
  public void getName_returnsCorrectDirectorName_CoenBrothers() {
    Director testDirector = new Director("Coen Brothers");
    assertEquals("Coen Brothers", testDirector.getName());
  }

  @Test
  public void save_savesDirectorIntoDatabase_true() {
    Director testDirector = new Director("Coen Brothers");
    testDirector.save();
    assertTrue(Director.all().get(0).equals(testDirector));
  }

  @Test
  public void all_returnsAllDirectorsInDB_true() {
    Director testDirector1 = new Director("Coen Brothers");
    testDirector1.save();
    Director testDirector2 = new Director("Wes Anderson");
    testDirector2.save();
    assertTrue(Director.all().get(0).equals(testDirector1));
    assertTrue(Director.all().get(1).equals(testDirector2));
  }

  @Test
  public void equals_ifDirectorNameIsTheSame_true() {
    Director testDirector1 = new Director("Coen Brothers");
    Director testDirector2 = new Director("Coen Brothers");
    assertTrue(testDirector1.equals(testDirector2));
  }

  @Test
  public void save_assignsIdToDirector_true() {
    Director testDirector = new Director("Coen Brothers");
    testDirector.save();
    assertEquals(testDirector.getId(), Director.all().get(0).getId());
  }

  @Test
  public void getId_returnsAnId_true() {
    Director testDirector = new Director("Coen Brothers");
    testDirector.save();
    assertTrue(testDirector.getId() > 0);
  }

  @Test
  public void find_returnsDirectorWithSameId_coenBrothers() {
    Director testDirector = new Director("Coen Brothers");
    testDirector.save();
    assertEquals(testDirector, Director.find(testDirector.getId()));
  }

  @Test
  public void getMovies_returnsAllMoviesWithSameDirectorId_List() {
    Director testDirector = new Director("Coen Brothers");
    testDirector.save();
    Movie testMovie1 = new Movie("Fargo", testDirector.getId(), "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie1.save();
    Movie testMovie2 = new Movie("Fargo", testDirector.getId(), "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie2.save();
    Movie[] movies = new Movie[] {testMovie1, testMovie2};
    assertTrue(testDirector.getMovies().containsAll(Arrays.asList(movies)));
  }

  @Test
  public void getDirectorReviews_returnsAllReviewsForGivenDirector_List() {
    Director testDirector = new Director("Coen Brothers");
    testDirector.save();
    Movie testMovie1 = new Movie("Fargo", testDirector.getId(), "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie1.save();
    Movie testMovie2 = new Movie("True Grit", testDirector.getId(), "A tough U.S. Marshal helps a stubborn teenager track down her father's murderer.", "Western", "2010-12-22");
    testMovie2.save();
    Review testReview1 = new Review(testMovie1.getId(), 89, "Good Movie");
    testReview1.save();
    Review testReview2 = new Review(testMovie2.getId(), 99, "Really Good Movie");
    testReview2.save();
    Review[] reviews = new Review[] {testReview1, testReview2};
    assertTrue(testDirector.getDirectorReviews().containsAll(Arrays.asList(reviews)));
  }

  @Test
  public void getDirectorRating_returnsAverageRatingForGivenDirector_float() {
    Director testDirector = new Director("Coen Brothers");
    testDirector.save();
    Movie testMovie1 = new Movie("Fargo", testDirector.getId(), "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie1.save();
    Movie testMovie2 = new Movie("True Grit", testDirector.getId(), "A tough U.S. Marshal helps a stubborn teenager track down her father's murderer.", "Western", "2010-12-22");
    testMovie2.save();
    Review testReview1 = new Review(testMovie1.getId(), 89, "Good Movie");
    testReview1.save();
    Review testReview2 = new Review(testMovie2.getId(), 99, "Really Good Movie");
    testReview2.save();
    assertEquals(94.0f, testDirector.getDirectorRating(), 0.1);
  }

  @Test
  public void getTopDirectors_returnsHighestRatedDirectors_list() {
    Director testDirector1 = new Director("Coen Brothers");
    testDirector1.save();
    Director testDirector2 = new Director("Wes Anderson");
    testDirector2.save();
    Movie testMovie1 = new Movie("Fargo", testDirector1.getId(), "Jerry works in his father-in-law's car dealership", "Crime", "1996-04-05");
    testMovie1.save();
    Movie testMovie2 = new Movie("Fantasic Mr.Fox", testDirector2.getId(), "A tough U.S. Marshal helps a stubborn teenager track down her father's murderer.", "Western", "2010-12-22");
    testMovie2.save();
    Review testReview1 = new Review(testMovie1.getId(), 89, "Good Movie");
    testReview1.save();
    Review testReview2 = new Review(testMovie2.getId(), 99, "Really Good Movie");
    testReview2.save();
    assertTrue(Director.getTopDirectors().get(0).equals(testDirector2));
    assertTrue(Director.getTopDirectors().get(1).equals(testDirector1));
  }

}
