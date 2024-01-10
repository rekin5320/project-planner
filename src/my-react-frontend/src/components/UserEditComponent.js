import React, {useState} from "react";
import axios from "axios";

const UserEditComponent = ({user, onUpdateUser}) => {
    const [name, setName] = useState(user.name);

    const handleNameChange = (e) => {
        setName(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.put(
                `http://localhost:3000/api/users/update/${user.id}`,
                {
                    name: name,
                }
            );
            response.data.google = user.google;
            onUpdateUser(response.data);
            console.log("Update successful:", response.data);
        } catch (error) {
            console.error("Error updating user:", error);
        }
    };

    return (
        <div>
            <h2 className="text-white">Edit User</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-2">
                    <label>
                        <span className="text-white mr-2">Name:</span>
                        <input
                            type="text"
                            className="myinput"
                            value={name}
                            onChange={handleNameChange}
                        />
                    </label>
                </div>

                <div>
                    <button
                        type="submit"
                        className="mybutton w-full"
                        >
                        Save Changesc/my
                    </button>
                </div>
            </form>
        </div>
    )
};

export default UserEditComponent;
