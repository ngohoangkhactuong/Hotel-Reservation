import React, { useState, useEffect } from "react"
import { addRoom, getRoomTypes } from "../utils/ApiFunctions"
import { Link } from "react-router-dom"

const AddRoom = () => {
    const [newRoom, setNewRoom] = useState({
        photo: null,
        roomType: "",
        roomPrice: ""
    })

    const [roomTypes, setRoomTypes] = useState([]) // State for room types
    const [message, setMessage] = useState({ text: "", type: "" }) // Success or error message
    const [imagePreview, setImagePreview] = useState("") // Preview of selected image
    const [loading, setLoading] = useState(false)

    // Fetch room types on component mount
    useEffect(() => {
        const fetchRoomTypes = async () => {
            try {
                const types = await getRoomTypes()
                setRoomTypes(types)
            } catch (error) {
                setMessage({ text: "Failed to load room types", type: "danger" })
            }
        }
        fetchRoomTypes()
    }, [])

    const handleRoomInputChange = (e) => {
        const { name, value } = e.target
        setNewRoom(prevState => ({
            ...prevState,
            [name]: name === "roomPrice" && !isNaN(value) ? parseInt(value) : value
        }))
    }

    const handleImageChange = (e) => {
        const selectedImage = e.target.files[0]
        if (selectedImage && !selectedImage.type.startsWith("image/")) {
            setMessage({ text: "Please upload a valid image file.", type: "danger" })
            setImagePreview("")
            setNewRoom({ ...newRoom, photo: null })
            return
        }
        setNewRoom({ ...newRoom, photo: selectedImage })
        setImagePreview(URL.createObjectURL(selectedImage))
        setMessage({ text: "", type: "" }) // Clear any previous error message
    }

    const handleSubmit = async (e) => {

        e.preventDefault();
        setLoading(true);

        try {
            const success = await addRoom(newRoom.photo, newRoom.roomType, newRoom.roomPrice);

            if (success) {

                setMessage({ text: "Room added successfully!", type: "success" });
                setNewRoom({ photo: null, roomType: "", roomPrice: "" });
                setImagePreview("");
            } else {

                setMessage({ text: "Error adding room", type: "danger" });
            }
        } catch (error) {

            setMessage({ text: `Error: ${error.message}`, type: "danger" });
        } finally {
            
            setLoading(false);
        }

        setTimeout(() => {
            setMessage({ text: "", type: "" });
        }, 3000);
    };


    return (
        <section className="container mt-5 mb-5">
            <div className="row justify-content-center">
                <div className="col-md-8 col-lg-6">
                    <h2 className="mt-5 mb-2">Add a New Room</h2>
                    {message.text && (
                        <div className={`alert alert-${message.type} fade show`}>{message.text}</div>
                    )}

                    <form onSubmit={handleSubmit}>
                        <div className="mb-3">
                            <label htmlFor="roomType" className="form-label">
                                Room Type
                            </label>
                            <select
                                required
                                className="form-select"
                                id="roomType"
                                name="roomType"
                                value={newRoom.roomType}
                                onChange={handleRoomInputChange}
                            >
                                <option value="">Select room type</option>
                                {roomTypes.map((type, index) => (
                                    <option key={index} value={type.name}>
                                        {type}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="mb-3">
                            <label htmlFor="roomPrice" className="form-label">
                                Room Price
                            </label>
                            <input
                                required
                                type="number"
                                className="form-control"
                                id="roomPrice"
                                name="roomPrice"
                                value={newRoom.roomPrice}
                                onChange={handleRoomInputChange}
                            />
                        </div>

                        <div className="mb-3">
                            <label htmlFor="photo" className="form-label">
                                Room Photo
                            </label>
                            <input
                                required
                                name="photo"
                                id="photo"
                                type="file"
                                className="form-control"
                                accept="image/*"
                                onChange={handleImageChange}
                            />
                            {imagePreview && (
                                <img
                                    src={imagePreview}
                                    alt="Preview room photo"
                                    style={{ maxWidth: "400px", maxHeight: "400px" }}
                                    className="mb-3"
                                />
                            )}
                        </div>

                        <div className="d-grid gap-2 d-md-flex mt-2">
                            <Link to={"/existing-rooms"} className="btn btn-outline-info">
                                Existing rooms
                            </Link>
                            <button type="submit" className="btn btn-outline-primary ml-5" disabled={loading}>
                                {loading ? "Saving..." : "Save Room"}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    )
}

export default AddRoom
