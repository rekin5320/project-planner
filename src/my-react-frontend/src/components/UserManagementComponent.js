import React, {useState, useEffect} from "react";
import axios from "axios";

const UserManagementComponent = () => {
    const [users, setUsers] = useState([]);
    const [newUserName, setNewUserName] = useState("");

    useEffect(() => {
        axios.get("/api/users/all")
            .then(response => {
                setUsers(response.data);
            })
            .catch(error => console.error("API call error:", error));
    }, []);

    const handleAddUser = (e) => {
        e.preventDefault();
        const newUser = {name: newUserName};
        axios.post("/api/users/add", newUser)
            .then(response => {
                setUsers([...users, response.data]);
                setNewUserName(""); // Clear the input field
            })
            .catch(error => console.error("Error adding user:", error));
    };

    const handleDeleteUser = (userId) => {
        axios.delete(`/api/users/delete/${userId}`)
            .then(() => {
                setUsers(users.filter(user => user.id !== userId));
            })
            .catch(error => console.error("Error deleting user:", error));
    };

    return (
        <div className="flex items-center justify-center">
            <div className="flex flex-col items-center justify-center overflow-hidden">
                <h2 className="text-3xl mb-2">Users</h2>
                {/* Scrollable container for the user list */}
                <div className="overflow-y-auto overflow-x-hidden max-h-[calc(100vh-150px)] w-full hide-scrollbar">
                    {users.map(user => (
                        <div key={user.id} className="bg-custom-blue shadow-md rounded-md p-4 mb-2 flex items-center justify-between w-full mr-2" >
                            <span className="text-gray-800 font-bold">ID: {user.id}</span>
                            <span className="text-gray-800 ml-2 mr-2">{user.name}</span>
                            <button
                                onClick={() => handleDeleteUser(user.id)}
                                className="bg-custom-gray text-white py-2 px-4 rounded hover:bg-pink-600"
                                >
                                Delete
                            </button>
                        </div>
                    ))}
                </div>
                {/* Form for adding a new user */}
                <form onSubmit={handleAddUser} className="mb-4">
                    <input
                        type="text"
                        value={newUserName}
                        onChange={(e) => setNewUserName(e.target.value)}
                        placeholder="Name"
                        className="mr-2 p-2 border border-custom-lightgray rounded bg-custom-lightgray"
                    />
                    <button
                        type="submit"
                        className="bg-custom-gray text-white py-2 px-4 rounded hover:bg-pink-600"
                        >
                        Add User
                    </button>
                </form>
            </div>
        </div>
    );
};

export default UserManagementComponent;
