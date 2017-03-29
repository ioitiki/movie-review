import org.sql2o.*;
import java.util.List;

public class Review {
  private int id;
  private int movieId;
  private int rating;
  private String review;

  public Review(int movieId, int rating, String review) {
    this.movieId = movieId;
    this.rating = rating;
    this.review = review;
  }

  public int getId() {
    return id;
  }

  public int getMovieId() {
    return movieId;
  }

  public int getRating() {
    return rating;
  }

  public String getReview() {
    return review;
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO reviews (movieId, rating, review) VALUES (:movieId, :rating, :review);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("movieId", this.movieId)
        .addParameter("rating", this.rating)
        .addParameter("review", this.review)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Review> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews;";
      return con.createQuery(sql)
        .executeAndFetch(Review.class);
    }
  }

  @Override
  public boolean equals(Object otherReview) {
    if (!(otherReview instanceof Review)) {
      return false;
    } else {
      Review newReview = (Review) otherReview;
      return this.getMovieId() == newReview.getMovieId() &&
             this.getRating() == newReview.getRating() &&
             this.getReview().equals(newReview.getReview()) &&
             this.getId() == newReview.getId();
    }
  }

  public static Review find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE id = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Review.class);
    }
  }

}
