package com.vvachev.movielibrary.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvachev.movielibrary.model.view.CommentViewModel;
import com.vvachev.movielibrary.repository.CommentRepository;
import com.vvachev.movielibrary.repository.MovieRepository;
import com.vvachev.movielibrary.repository.UserRepository;
import com.vvachev.movielibrary.service.interfaces.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService {
	
	private final MovieRepository movieRepository;
	  private final UserRepository userRepository;
	  private final CommentRepository commentRepository;
	  
	  
@Autowired
	public CommentServiceImpl(MovieRepository movieRepository, UserRepository userRepository,
			CommentRepository commentRepository) {
		this.movieRepository = movieRepository;
		this.userRepository = userRepository;
		this.commentRepository = commentRepository;
	}



	@Override
	public List<CommentViewModel> getComments(Long movieId) {
//		    Optional<MovieEntity>  movieEnity= movieRepository.
//			        findById(movieId);
//
//			    if (movieEnity.isEmpty()) {
//			      throw new ObjectNotFoundException(String.format("Movie with id $s was not found", movieId);
//			    }
//
//			    return movieEnity.
//			        get().
//			        getComments().
//			        stream().
//			        map(this::mapAsComment).
//			        collect(Collectors.toList());
//			  }
		return null;
	}

}
