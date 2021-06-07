import axios from 'axios'
import Qs from 'qs'

export default function ajax(url, data = {}, file = '') {

    return new Promise((resolve, reject) => {
        let promise
        data = Qs.stringify(data);
        if (file) {
            promise = axios.post(url, data, { headers: { 'Content-Type': 'multipart/form-data' } })
        } else {
            promise = axios.post(url, data, { headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' } })
        }
        promise.then(response => {
            resolve(response.data)
        }).catch(error => {
            this.$message.error(error);
        })
    })
}