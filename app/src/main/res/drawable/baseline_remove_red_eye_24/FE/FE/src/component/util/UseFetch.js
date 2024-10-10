import axios from 'axios';

const fetchData = (url, param = {}) => {
  try {
    return axios.get(url, {
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then(response => {
        if (response.status === 200) {
          return response.data;
        } else {
          return Promise.reject("ERROR");
        }
      })
      .catch(error => {
        console.error('Fetch error:', error);
        return "ERROR";
      });
  } catch (err) {
    console.error('Fetch error:', err);
    return "ERROR";
  }
};

export default fetchData;
