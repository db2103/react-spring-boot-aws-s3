import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import { useDropzone } from 'react-dropzone'
import './App.css';
import { BASE_URL } from './constants/constants';


const UserProfile = () => {

  const [userProfiles, setUserProfiles] = useState([]);

  const fetchUserProfile = useCallback(async () => {
    axios.get(BASE_URL)
      .then(res => setUserProfiles(res.data))
      .catch(err => console.error(err));
  }, []);

  useEffect(() => {
    fetchUserProfile();
  }, [fetchUserProfile]);

  return (
    userProfiles.map((userProfile, index) => (
      <div key={index}>
        {
          userProfile.userProfileImageLink ?
            <img src={`${BASE_URL}/${userProfile.userProfileId}/image/download?time=${Date.now()}`} alt="profile pic"/> : null
        }
        <br />
        <br />
        <h1>{userProfile.userName}</h1>
        <p>{userProfile.userProfileId}</p>
        <MyDropzone {...userProfile} callback={() => fetchUserProfile()} />
        <br />
      </div>
    ))
  )
}

function MyDropzone({ userProfileId, callback }) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);

    const formData = new FormData();
    formData.append("file", file);

    axios.post(`${BASE_URL}/${userProfileId}/image/upload`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
      .then(() => {
        console.log("File successfully uploaded");
        callback();
      }
      )
      .catch(err => console.error(err));

  }, [userProfileId, callback]);

  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop })

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the pic here ...</p> :
          <p>Drag 'n' drop profile pic , or click to profile pic</p>
      }
    </div>
  )
}

function App() {
  return (
    <div className="App">
      <UserProfile />
    </div>
  );
}

export default App;
