/**
 * MIT License
 *
 * Copyright (c) 2022 linjicong
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import request from './cloud'

/**
 * 统一账单 API（5 个只读端点）。
 *
 * 约定：
 * - 复用 cloud.js 的 axios 实例（baseURL '/api'，响应拦截器返回完整 AxiosResponse）
 * - 业务数据取 response.data
 * - providers / accounts 传逗号分隔字符串；空数组不传该参数（不要传空串）
 */
export const billApi = {
  /**
   * 总览 KPI
   * @param {{startMonth:string,endMonth:string,providers?:string,accounts?:string}} params
   */
  getKpi(params) {
    return request.get('/bill/kpi', { params })
  },

  /**
   * 历史趋势
   * @param {{startMonth:string,endMonth:string,providers?:string,accounts?:string,months:number}} params
   */
  getTrend(params) {
    return request.get('/bill/trend', { params })
  },

  /**
   * 账号维度
   * @param {{startMonth:string,endMonth:string,providers?:string,accounts?:string,topN?:number}} params
   */
  getAccounts(params) {
    return request.get('/bill/accounts', { params })
  },

  /**
   * 明细分页
   * @param {{startMonth:string,endMonth:string,providers?:string,accounts?:string,
   *          productMain?:string,chargingMode?:string,pageNum?:number,pageSize?:number,
   *          sortField?:string,sortOrder?:string}} params
   */
  getDetails(params) {
    return request.get('/bill/details', { params })
  },

  /**
   * 产品 TOP
   * @param {{startMonth:string,endMonth:string,providers?:string,accounts?:string,
   *          topN?:number,dimension?:string}} params
   */
  getProductTop(params) {
    return request.get('/bill/products/top', { params })
  }
}

export default billApi
