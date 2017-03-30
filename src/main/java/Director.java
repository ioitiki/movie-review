import org.sql2o.*;
import java.util.List;

public class Director {
  private String name;
  private int id;

  public Director(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO directors (name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Director> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM directors;";
      return con.createQuery(sql)
        .executeAndFetch(Director.class);
    }
  }

  @Override
  public boolean equals(Object otherDirector) {
    if (!(otherDirector instanceof Director)) {
      return false;
    } else {
      Director newDirector = (Director) otherDirector;
      return this.getName().equals(newDirector.getName()) &&
             this.getId() == newDirector.getId();
    }
  }

  public static Director find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM directors WHERE id = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Director.class);
    }
  }

  public List<Movie> getMovies() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM movies WHERE directorId = :directorId;";
      return con.createQuery(sql)
        .addParameter("directorId", this.id)
        .executeAndFetch(Movie.class);
    }
  }

  public List<Review> getDirectorReviews() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews AS a WHERE movieId IN (SELECT id FROM movies WHERE directorId = :directorId) ORDER By a.rating desc;";
      return con.createQuery(sql)
        .addParameter("directorId", this.id)
        .executeAndFetch(Review.class);
    }
  }

  public Float getDirectorRating() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT AVG(rating) FROM reviews WHERE movieId IN (SELECT id FROM movies WHERE directorId = :directorId);";
      return con.createQuery(sql)
        .addParameter("directorId", this.id)
        .executeScalar(Float.class);
    }
  }

  public static List<Director> getTopDirectors() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM directors AS a ORDER BY (SELECT COALESCE(AVG(rating), 0) FROM reviews WHERE movieId IN (SELECT id FROM movies WHERE directorId = a.id)) desc;";
      return con.createQuery(sql)
        .executeAndFetch(Director.class);
    }
  }

  public void updateDirector(String name) {
    this.name = name;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE directors SET name = :name WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("name", this.name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void deleteDirector() {
    List<Movie> movies = this.getMovies();
    for (Movie movie : movies) {
      movie.deleteMovie();
    }
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM directors WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static List<Director> searchDirector(String input) {
    String newInput = "%" + input + "%";
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM directors AS a WHERE lower(name) LIKE lower(:input) ORDER BY (SELECT COALESCE(AVG(rating), 0) FROM reviews WHERE movieId IN (SELECT id FROM movies WHERE directorId = a.id)) desc;";
      return con.createQuery(sql)
        .addParameter("input", newInput)
        .executeAndFetch(Director.class);
    }
  }

  public Integer getDirectorReviewCount() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT COUNT(rating) FROM reviews WHERE movieId IN (SELECT id FROM movies WHERE directorId = :directorId);";
      return con.createQuery(sql)
        .addParameter("directorId", this.id)
        .executeScalar(Integer.class);
    }
  }

}
