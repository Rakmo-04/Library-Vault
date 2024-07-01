import { useOktaAuth } from "@okta/okta-react";
import { useState,useEffect } from "react";
import MessageModel from "../../../models/MessageModel";
import { SpinnerLoading } from "../../Utils/SpinnerLoading";
import { Pagination } from "../../Utils/Pagination";
import { AdminMessage } from "./AdminMessage";
import AdminMessageRequest from "../../../models/AdminMessageRequest";

export const AdminMessages=()=>{

    const {authState} = useOktaAuth();

    const [isLodingMessages,setIsLoadingMessages] = useState(true);

    const [httpError,setHttpError] = useState(null);

    const [messages,setMessages] = useState<MessageModel[]>([]);

    const [messagesPerPage] = useState(5);

    const [currentPage,setCurrentPage] = useState(1);

    const [totalPages,setTotalPages] = useState(0);

    const [btnSubmit,setBtnSubmit] = useState(false);

    useEffect(()=>{
        const fetchUserMessages = async () =>
            {
                if(authState && authState.isAuthenticated){
                    const url = `http:localhost:8080/api/messages/search/findByClosed/?closed=false&page=${currentPage-1}&size=${messagesPerPage}`;
                    const requestOption = {
                        method: "GET",
                        headers: {
                            Authorization:`Bearer ${authState.accessToken?.accessToken}`,
                        'Content-Type':'application/json'                        }
                    };

                    const messageResponse = await fetch(url,requestOption);
                   
                    if(!messageResponse.ok){
                        throw new Error('Something went wrong');
                    }
                    const messageResponseJson = await messageResponse.json();
                    setMessages(messageResponseJson._embedded.messages);
                    setTotalPages(messageResponseJson.page.totalPages);


                }
                setIsLoadingMessages(false);


            }
            fetchUserMessages().catch((error)=>{
                setIsLoadingMessages(false);
                setHttpError(error.message);
            })
            window.scrollTo(0,0);
    },[authState,currentPage,btnSubmit]);


    if(isLodingMessages){
        return (
<SpinnerLoading/>
        );
    }

    if(httpError){
        return (
            <div className="container m-5">
                <p>{httpError}</p>
            </div>
        );
    }

    async function submitResponseToQuestion(id:number,response:string){
        const url = `http:localhost:8080/api/messages/secure/admin/message`;
        if(authState && authState.isAuthenticated && id !== null && response !==''){
            const messageAdminRequestModel :AdminMessageRequest = new AdminMessageRequest(id,response);
            const requestOption = {
                method: "PUT",
                headers: {
                    Authorization:`Bearer ${authState.accessToken?.accessToken}`,
                    'Content-Type':'application/json'
                },
                body:JSON.stringify(messageAdminRequestModel),

            };

            const messageAdminRequestModelResponse = await fetch(url,requestOption);
            if(!messageAdminRequestModelResponse.ok){
                throw new Error('Something went wrong');
            }
            setBtnSubmit(!btnSubmit);
        

        }
    }
    
    const paginate = (pageNumber: number) => setCurrentPage(pageNumber);


    return (
        <div className="mt-3">
            {messages.length > 0 ?
            <>
            <h5>Pending Q/A: </h5>
            {messages.map(message=>{
                // <p>Question that need a Response</p>
                    <AdminMessage message={message} key={message.id} submitResponseToQueston={submitResponseToQuestion}/>
            })}
            </>
            :
            <h5>No Pending Q/A</h5>
            }
            {totalPages > 1 && <Pagination currentPage={currentPage} totalPages={totalPages} paginate={paginate}/>}
        </div>
    );
}