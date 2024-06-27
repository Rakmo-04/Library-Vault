import { useEffect, useState, useCallback } from "react";
import ReviewModel from "../../../models/ReviewModel";
import { SpinnerLoading } from "../../Utils/SpinnerLoading";
import { Review } from "../../Utils/Review";
import { Pagination } from "../../Utils/Pagination";

export const ReviewListPage = () => {
  const [reviews, setReviews] = useState<ReviewModel[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [httpError, setHttpError] = useState<string | null>(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [reviewsPerPage] = useState(5);
  const [totalAmountOfReviews, setTotalAmountOfReviews] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const bookId = (window.location.pathname).split('/')[2];

  const fetchBookReviews = useCallback(async () => {
    try {
      const reviewUrl: string = `http://localhost:8080/api/reviews/search/findByBookId?bookId=${bookId}&page=${currentPage - 1}&size=${reviewsPerPage}`;

      const responseReview = await fetch(reviewUrl);

      if (!responseReview.ok) {
        throw new Error("Something went wrong!");
      }

      const responseJsonReviews = await responseReview.json();
      const responseData = responseJsonReviews._embedded.reviews;

      setTotalAmountOfReviews(responseJsonReviews.page.totalElements);
      setTotalPages(responseJsonReviews.page.totalPages);

      const loadedReviews: ReviewModel[] = responseData.map((review: any) => ({
        id: review.id,
        userEmail: review.userEmail,
        date: review.date,
        rating: review.rating,
        book_id: review.book_id,
        reviewDescription: review.reviewDescription,
      }));

      setReviews(loadedReviews);
      setIsLoading(false);
    } catch (error: any) {
      setIsLoading(false);
      setHttpError(error.message);
    }
  }, [bookId, currentPage, reviewsPerPage]);

  useEffect(() => {
    fetchBookReviews();
  }, [fetchBookReviews]);

  if (isLoading) {
    return <SpinnerLoading />;
  }

  if (httpError) {
    return (
      <div className="container m-5">
        <p>{httpError}</p>
      </div>
    );
  }

  const indexOfLastReview: number = currentPage * reviewsPerPage;
  const indexOfFirstReview: number = indexOfLastReview - reviewsPerPage;

  let lastItem = reviewsPerPage * currentPage <= totalAmountOfReviews ? reviewsPerPage * currentPage : totalAmountOfReviews;

  const paginate = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

  return (
    <div className="container m-5">
      <div>
        <h3>Comments: ({reviews.length})</h3>
      </div>
      <p>
        {indexOfFirstReview + 1} to {lastItem} of {totalAmountOfReviews} items:
      </p>
      <div className="row">
        {reviews.map(review => (
          <Review review={review} key={review.id} />
        ))}
      </div>
      {totalPages > 1 && <Pagination currentPage={currentPage} totalPages={totalPages} paginate={paginate} />}
    </div>
  );
};
