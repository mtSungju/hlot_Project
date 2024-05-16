import axios from "axios";

const BASE_URL = import.meta.env.VITE_BASE_URL;
const BE_MANAGEMENT_PORT = import.meta.env.VITE_BE_MANAGEMENT_PORT;
const REQUEST_URL = `${BASE_URL}:${BE_MANAGEMENT_PORT}`;

export default {

  /* ESTIMATE 목록조회 */
  estimates(){
    return axios.get(REQUEST_URL + '/api/estimates').then(res => {
      return res.data.data;
    });
  },

  /* ESTIMATE 상세조회 */
  estimate(id) {
    return axios.get(REQUEST_URL + `/api/estimate/${id}`).then(res => {
      console.log(res);
      return res.data.data;
    });
  },

  /* PROD 목록조회 */
  prods(id) {
    return axios.get(REQUEST_URL + `/api/prods/${id}`).then(res => {
      return res.data.data;
    });
  },

}
