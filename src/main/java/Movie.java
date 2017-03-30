import org.sql2o.*;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

public class Movie {
  private String title;
  private int id;
  private int directorId;
  private String description;
  private String genre;
  private String releaseDate;

  public Movie(String title, int directorId, String description, String genre, String releaseDate) {
    this.title = title;
    this.directorId = directorId;
    this.description = description;
    this.genre = genre;
    // this.releaseDate = new Timestamp(Date.valueOf(releaseDate).getTime());
    this.releaseDate = releaseDate;

  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public int getDirectorId() {
    return directorId;
  }

  public String getDescription() {
    return description;
  }

  public String getGenre() {
    return genre;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO movies (title, directorId, description, genre, releaseDate) VALUES (:title, :directorId, :description, :genre, :releaseDate);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("directorId", this.directorId)
        .addParameter("description", this.description)
        .addParameter("genre", this.genre)
        .addParameter("releaseDate", this.releaseDate)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Movie> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM movies;";
      return con.createQuery(sql)
        .executeAndFetch(Movie.class);
    }
  }

  @Override
  public boolean equals(Object otherMovie) {
    if (!(otherMovie instanceof Movie)) {
      return false;
    } else {
      Movie newMovie = (Movie) otherMovie;
      return this.getTitle().equals(newMovie.getTitle()) &&
             this.getDirectorId() == newMovie.getDirectorId() &&
             this.getDescription().equals(newMovie.getDescription()) &&
             this.getGenre().equals(newMovie.getGenre()) &&
             this.getReleaseDate().equals(newMovie.getReleaseDate()) &&
             this.getId() == newMovie.getId();
    }
  }

  public static Movie find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM movies WHERE id = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Movie.class);
    }
  }

  public List<Review> getReviews() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE movieId = :movieId;";
      return con.createQuery(sql)
        .addParameter("movieId", this.id)
        .executeAndFetch(Review.class);
    }
  }

  public Float getMovieRating() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT AVG(rating) FROM reviews WHERE movieId = :movieId;";
      return con.createQuery(sql)
        .addParameter("movieId", this.id)
        .executeScalar(Float.class);
    }
  }

  public static List<Movie> getTopMovies() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM movies AS a ORDER BY (SELECT COALESCE(AVG(rating), 0) FROM reviews WHERE movieId = a.id) desc;";
      return con.createQuery(sql)
        .executeAndFetch(Movie.class);
    }
  }

  public void updateMovie(String title, int directorId, String description, String genre, String releaseDate) {
    this.title = title;
    this.directorId = directorId;
    this.description = description;
    this.genre = genre;
    // this.releaseDate = new Timestamp(Date.valueOf(releaseDate).getTime());
    this.releaseDate = releaseDate;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE movies SET (title, directorId, description, genre, releaseDate) = (:title, :directorId, :description, :genre, :releaseDate) WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("title", this.title)
        .addParameter("directorId", this.directorId)
        .addParameter("description", this.description)
        .addParameter("genre", this.genre)
        .addParameter("releaseDate", this.releaseDate)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void deleteMovie() {
    List<Review> reviews = this.getReviews();
    for (Review review : reviews) {
      review.deleteReview();
    }
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM movies WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static List<Movie> searchMovie(String input) {
    String newInput = "%" + input + "%";
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM movies WHERE lower(title) LIKE lower(:input);";
      return con.createQuery(sql)
        .addParameter("input", newInput)
        .executeAndFetch(Movie.class);
    }
  }

}
