import axios from 'axios'
import errorParser from '@/utils/error-parser'

export default {
  /**
   * 로그인 요청에 대한 인증
   * @param {Object} detail 로그인 정보
   */
  authenticate(detail: Object) {
    return new Promise((resolve, reject) => {
      axios
        .post('/authentications', detail)
        .then(({ data }) => {
          resolve(data)
        })
        .catch((error) => {
          reject(errorParser.parse(error))
        })
    })
  }
}
