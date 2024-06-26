import { ReturnBooks } from "./ReturnBooks";
import { useEffect,useState } from "react";
import BookModel from "../../../models/BookModel";
import { SpinnerLoading } from "../../Utils/SpinnerLoading";
import { Link } from "react-router-dom";



export const Carousel = () => {

    const [books,setBooks]= useState<BookModel[]>([]);
    const [isLoading,setLoading] = useState(true);
    const [httpError,setHttpError] = useState(null);

    useEffect(()=>{

        const fetchBooks = async ()=>{
            const baseUrl : string = "http://localhost:8080/api/books";

            const url : string = `${baseUrl}?page=0&size=9`;

            const response = await fetch(url);

            if(!response.ok){
                throw new Error('Something went wrong!');

            }

            const reponseJson = await response.json();

            const reponseData = reponseJson._embedded.books;

            const loadBooks:BookModel[]=[];

            for(const key in reponseData){
                loadBooks.push({
                    id:reponseData[key].id,
                    title:reponseData[key].title,
                    author:reponseData[key].author,
                    description:reponseData[key].description,
                    copies:reponseData[key].copies,
                    copiesAvailable:reponseData[key].copiesAvailable,
                    category:reponseData[key].category,
                    img:reponseData[key].img,
                })
            }
            setBooks(loadBooks);
            setLoading(false);
        };

        fetchBooks().catch((error:any)=>{
            setLoading(false);
            setHttpError(error.message);
        })
    },[]);

    if(isLoading){
        return(
            <SpinnerLoading/>
        )
    }

    if(httpError){
        return(
            <div className="container m-5">
                <p>{httpError}</p>
            </div>
        )
    }

    return (
        <div className="container mt-5" style={{ height: 550 }}>
            <div className="homepage-carousel-title">

                <h3>Find your next "I stayed up too late reading" book here</h3>

            </div>
            <div id="carouselExampleControls" className="carousel carousel-dark slide mt-5
            d-none d-lg-block" data-bs-interval='false'>
                {/*Desktop*/}
                <div className="carousel-inner">
                    <div className="carousel-item active">
                        <div className="row d-flex justify-content-center align-items-center">
                        {
                                books.slice(0,3).map(book=>(
                                    <ReturnBooks book = {book} key={book.id}/>
                                ))
                            }                        </div>
                    </div>
                    <div className="carousel-item ">
                        <div className="row d-flex justify-content-center align-items-center">
                        {
                                books.slice(3,6).map(book=>(
                                    <ReturnBooks book = {book} key={book.id}/>
                                ))
                            }
                        </div>
                    </div>
                    <div className="carousel-item ">
                        <div className="row d-flex justify-content-center align-items-center">
                            {
                                books.slice(6,9).map(book=>(
                                    <ReturnBooks book = {book} key={book.id}/>
                                ))
                            }
                        </div>
                    </div>
                </div>
                <button className="carousel-control-prev " type="button" data-bs-target='#carouselExampleControls' data-bs-slide='prev'>
                    <span className="carousel-control-prev-icon" aria-hidden='true'></span>
                    <span className="visually-hidden">Previous</span>
                </button>
                <button className="carousel-control-next " type="button"
                    data-bs-target='#carouselExampleControls'
                    data-bs-slide='next'>
                    <span className="carousel-control-next-icon" aria-hidden='true'></span>
                    <span className="visually-hidden">Next</span>
                </button>

            </div>
            {/* Mobile */}
            <div className="d-lg-none mt-3">
                <div className="row d-flex justify-content-center align-items-center">

                  <ReturnBooks book={books[7]} key = {books[7].id} />
                  
                </div>
            </div>
            <div className="homepage-carousel-title mt-3">
                <Link className="bt btn-outline-secondary btn-lg" to="/search">View More</Link>
            </div>
        </div>


    );
}