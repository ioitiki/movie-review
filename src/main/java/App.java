import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("topMovies", Movie.getTopMovies());
      model.put("topDirectors", Director.getTopDirectors());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/admin", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("directors", Director.all());
      model.put("template", "templates/admin.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/directors", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("directors", Director.getTopDirectors());
      model.put("template", "templates/directors.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/movies", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("movies", Movie.getTopMovies());
      model.put("template", "templates/movies.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/directors", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Director newDirector = new Director(name);
      newDirector.save();
      response.redirect("/admin");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/movies", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String title = request.queryParams("title");
      int directorId = Integer.parseInt(request.queryParams("directorId"));
      String description = request.queryParams("description");
      String genre = request.queryParams("genre");
      String releaseDate = request.queryParams("releaseDate");
      Movie newMovie = new Movie(title, directorId, description, genre, releaseDate);
      newMovie.save();
      response.redirect("/movies");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/directors/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Director director = Director.find(Integer.parseInt(request.params(":id")));
      model.put("director", director);
      model.put("template", "templates/director.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/directors/:id/movies/:movieId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Director director = Director.find(Integer.parseInt(request.params(":id")));
      Movie movie = Movie.find(Integer.parseInt(request.params(":movieId")));
      model.put("movie", movie);
      model.put("director", director);
      model.put("template", "templates/movie.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/directors/:id/movies/:movieId/reviews/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int rating = Integer.parseInt(request.queryParams("rating"));
      String review = request.queryParams("review");
      int directorId = Integer.parseInt(request.params(":id"));
      int movieId = Integer.parseInt(request.params(":movieId"));
      Review newReview = new Review(movieId, rating, review);
      newReview.save();
      String url = String.format("/directors/%d/movies/%d", directorId, movieId);
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/directors/:id/movies/:movieId/reviews/:reviewId/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Movie movie = Movie.find(Integer.parseInt(request.params(":movieId")));
      Review review = Review.find(Integer.parseInt(request.params(":reviewId")));
      model.put("movie", movie);
      model.put("review", review);
      model.put("template", "templates/review-edit-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/directors/:id/movies/:movieId/reviews/:reviewId/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int rating = Integer.parseInt(request.queryParams("rating"));
      String review = request.queryParams("review");
      int directorId = Integer.parseInt(request.params(":id"));
      int movieId = Integer.parseInt(request.params(":movieId"));
      Review userReview = Review.find(Integer.parseInt(request.params(":reviewId")));
      userReview.updateReview(rating, review);
      String url = String.format("/directors/%d/movies/%d", directorId, movieId);
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/directors/:id/movies/:movieId/reviews/:reviewId/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int directorId = Integer.parseInt(request.params(":id"));
      int movieId = Integer.parseInt(request.params(":movieId"));
      Review review = Review.find(Integer.parseInt(request.params(":reviewId")));
      review.deleteReview();
      String url = String.format("/directors/%d/movies/%d", directorId, movieId);
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/directors/:id/movies/:movieId/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Movie movie = Movie.find(Integer.parseInt(request.params(":movieId")));
      model.put("movie", movie);
      model.put("directors", Director.all());
      model.put("template", "templates/movie-edit-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/directors/:id/movies/:movieId/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String title = request.queryParams("title");
      int directorId = Integer.parseInt(request.queryParams("directorId"));
      int movieId = Integer.parseInt(request.params(":movieId"));
      String description = request.queryParams("description");
      String genre = request.queryParams("genre");
      String releaseDate = request.queryParams("releaseDate");
      Movie movie = Movie.find(movieId);
      movie.updateMovie(title, directorId, description, genre, releaseDate);
      String url = String.format("/directors/%d/movies/%d", directorId, movieId);
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/directors/:id/movies/:movieId/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int directorId = Integer.parseInt(request.params(":id"));
      int movieId = Integer.parseInt(request.params(":movieId"));
      Movie movie = Movie.find(movieId);
      movie.deleteMovie();
      String url = String.format("/directors/%d", directorId);
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/directors/:id/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Director director = Director.find(Integer.parseInt(request.params(":id")));
      model.put("director", director);
      model.put("template", "templates/director-edit-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/directors/:id/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      int directorId = Integer.parseInt(request.params(":id"));
      Director director = Director.find(directorId);
      director.updateDirector(name);
      String url = String.format("/directors/%d", directorId);
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/directors/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int directorId = Integer.parseInt(request.params(":id"));
      Director director = Director.find(directorId);
      director.deleteDirector();
      response.redirect("/directors");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
