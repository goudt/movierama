package com.example.movierama.ui.controller;

import com.example.movierama.movie.adapter.in.web.MovieController;
import com.example.movierama.movie.adapter.out.persistence.MovieRatingPort;
import com.example.movierama.movie.domain.Movie;
import com.example.movierama.persistence.domain.MovieDto;
import com.example.movierama.ui.domain.Pagination;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MVCMoviesController {

    private final MovieController movieController;

    private Pagination page = Pagination.builder()
            .page(0)
            .pageSize(5)
            .totalPages(100)
            .sortBy("date").build();

    private void setOrderBy(@PathParam("sortBy") String sortBy) {
        if (sortBy != null && !sortBy.isEmpty()) {
            page.setSortBy(sortBy);
        } else {
            page.setSortBy("date");
        }
    }

    private String getUserName() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    private List<Pair<Integer, String>> getLinkPairs(Page<Movie> movies, Long owner, String order) {
        List<Pair<Integer, String>> pageLinks = new ArrayList();
        for (int i = 0; i < movies.getTotalPages(); i++) {
            pageLinks.add(Pair.of(i,
                    "/movies" + (owner != null ? "/" + owner : "") + "?page=" + i + (order != null ? "&sortBy=" + order : "")));
        }
        return pageLinks;
    }

    private void setModelPaginationAttributes(Model model, Page<Movie> movies, Long owner) {
        page = Pagination.builder()
                .page(movies.getNumber())
                .pageSize(page.getPageSize())
                .totalPages(movies.getTotalPages())
                .sortBy(page.getSortBy())
                .build();
        model.addAttribute("movies", movies.getContent());
        model.addAttribute("sortBy", page.getSortBy());
        model.addAttribute("pages", getLinkPairs(movies, owner, page.getSortBy()));
        model.addAttribute("page", page);
    }

    @RequestMapping
    public String listAllMovies(@RequestParam("sortBy") Optional<String> sortBy, Model model) {
        return "redirect:/movies";
    }

    @GetMapping("/movies/{ownerId}")
    public String listOwnersMovies(@PathVariable("ownerId") Long ownerId,
                                   @RequestParam("sortBy") Optional<String> sortBy,
                                   @RequestParam("page") Optional<Integer> pageIdx,
                                   Model model) {
        setOrderBy(sortBy.orElse("date"));
        final Page<Movie> movies = movieController.listMoviesByOwner(
                ownerId, pageIdx.orElse(0), page.getPageSize(), page.getSortBy());
        setModelPaginationAttributes(model, movies, ownerId);
        model.addAttribute("allMoviesBtn", true);
        return "index";
    }

    @GetMapping("/movies")
    public String sortMovies(Model model,
                             @RequestParam("sortBy") Optional<String> sortBy,
                             @RequestParam("page") Optional<Integer> pageIdx
    ) {
        setOrderBy(sortBy.orElse("date"));
        final Page<Movie> movies = movieController.listMovies(
                pageIdx.orElse(page.getPage()), page.getPageSize(), page.getSortBy());
        setModelPaginationAttributes(model, movies, null);
        return "index";
    }

    @GetMapping("movies/hate")
    public ModelAndView hateMovie(ModelMap model, @PathParam("movieId") Long movieId) {
        try {
            movieController.rateMovie(movieId, getUserName(), MovieRatingPort.Vote.HATE);
        } catch (ResponseStatusException e) {
            model.addAttribute("errormsg", e.getReason());
        }
        return new ModelAndView("redirect:/movies");
    }

    @GetMapping("/movies/like")
    public ModelAndView likeMovie(ModelMap model, @PathParam("movieId") Long movieId) {
        try {
            movieController.rateMovie(movieId, getUserName(), MovieRatingPort.Vote.LIKE);
        } catch (ResponseStatusException e) {
            model.addAttribute("errormsg", e.getReason());
//            bindingResult.addError(new ObjectError("voteMyMovieError", e.getReason()));

        }
        return new ModelAndView("redirect:/movies");
//        return new ModelAndView("redirect:/movies");

    }

    @GetMapping("/movies/submit")
    public String submitMovieForm(MovieDto movieDto, BindingResult bindingResult, Model model) {
        model.addAttribute("movieDto", new MovieDto("title", "description", null));
        return "submit-movie";
    }

    @PostMapping("/movies/submit")
    public String submitMovie(@Valid MovieDto movie, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/submit";
        }
        movie.setUser(getUserName());
        movieController.submitMovie(movie);
        return "redirect:/movies";
    }

    @AllArgsConstructor
    class Link {
        public int index;
        public String value;
    }

}
