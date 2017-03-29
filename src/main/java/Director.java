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
      String sql = "SELECT * FROM directors AS a ORDER BY (SELECT AVG(rating) FROM reviews WHERE movieId IN (SELECT id FROM movies WHERE directorId = a.id)) asc;";
      return con.createQuery(sql)
        .executeAndFetch(Director.class);
    }
  }

}
