

class AddBookRequest{
    title:string;
    author:string;
    description:string;
    copies:number;
    category:string;
    img?:string;

    constructor(title:string,author:string,description:string,copies:number,category:string,img?:string){
        this.author=author;
        this.category=category;
        this.copies=copies;
        this.description=description;
        this.img=img;
        this.title=title;

    }


}

export default AddBookRequest;