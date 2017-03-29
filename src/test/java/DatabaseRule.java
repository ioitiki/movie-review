import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/movie_review_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteDirectors = "DELETE FROM directors *;";
      String deleteMovies= "DELETE FROM movies *;";
      String deleteReviews= "DELETE FROM reviews *;";
      con.createQuery(deleteDirectors).executeUpdate();
      con.createQuery(deleteMovies).executeUpdate();
      con.createQuery(deleteReviews).executeUpdate();
    }
  }

}
