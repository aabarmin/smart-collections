import Header from "../component/header";
import Footer from "../component/footers";
import VinylDetails from "../component/vinyl-details";
import ToastContainer from "../component/toast-container";

export default function PageSingle() {
    return (
        <>
            <Header />
            <VinylDetails />
            <Footer />
            <ToastContainer />
        </>
    );
}