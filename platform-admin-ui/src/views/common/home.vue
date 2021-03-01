<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card shadow="hover" v-for="item in dailySummary" :key="item.refDate">
          <div slot="header" class="clearfix">
            <b>小程序概况趋势(<b>{{item.refDate}}</b>)</b>
            <el-date-picker
              style="float: right;width: 140px"
              v-model="value1"
              type="date"
              @change="changeValue1"
              value-format="yyyy-MM-dd"
              placeholder="选择日期"
              :picker-options="pickerOptions">
            </el-date-picker>
          </div>
          <el-form :inline="true">
            <el-card shadow="hover">
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.visitTotal" class="item">
                    <el-button type="primary" round size="small">累计访问人数</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.sharePv" class="item">
                    <el-button type="success" round size="small">转发次数</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.shareUv" class="item">
                    <el-button type="warning" round size="small">转发人数</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
            </el-card>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="24">
        <el-card shadow="hover" v-for="item in dailyVisit" :key="item.refDate">
          <div slot="header" class="clearfix">
            <b>小程序日访问趋势(<b>{{item.refDate}}</b>)</b>
            <el-date-picker
              style="float: right;width: 140px"
              v-model="value2"
              type="date"
              @change="changeValue2"
              value-format="yyyy-MM-dd"
              placeholder="选择日期"
              :picker-options="pickerOptions">
            </el-date-picker>
          </div>
          <el-form :inline="true">
            <el-card shadow="hover">
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.sessionCnt" class="item">
                    <el-button type="primary" round size="small">打开次数</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.visitPv" class="item">
                    <el-button type="success" round size="small">访问次数</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.visitUv" class="item">
                    <el-button type="warning" round size="small">访问人数</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.visitUvNew" class="item">
                    <el-button type="danger" round size="small">新用户数</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.stayTimeUv" class="item">
                    <el-button type="primary" round size="small">人均停留时长</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.stayTimeSession" class="item">
                    <el-button type="success" round size="small">次均停留时长</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.visitDepth" class="item">
                    <el-button type="warning" round size="small">平均访问深度</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
            </el-card>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="24">
        <el-card shadow="hover" v-for="item in weeklyVisit" :key="item.refDate">
          <div slot="header" class="clearfix">
            <b>小程序周访问趋势(<b>{{item.refDate}}</b>)</b>
            <el-date-picker
              style="float: right;width: 160px"
              v-model="value3"
              type="week"
              format="yyyy 第 WW 周"
              @change="changeValue3"
              value-format="yyyy-MM-dd"
              placeholder="选择周"
              :picker-options="pickerWeekOptions">
            </el-date-picker>
          </div>
          <el-form :inline="true">
            <el-card shadow="hover">
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.sessionCnt" class="item">
                    <el-button type="primary" round size="small">打开次数</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.visitPv" class="item">
                    <el-button type="success" round size="small">访问次数</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.visitUv" class="item">
                    <el-button type="warning" round size="small">访问人数</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.visitUvNew" class="item">
                    <el-button type="danger" round size="small">新用户数</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.stayTimeUv" class="item">
                    <el-button type="primary" round size="small">人均停留时长</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.stayTimeSession" class="item">
                    <el-button type="success" round size="small">次均停留时长</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.visitDepth" class="item">
                    <el-button type="warning" round size="small">平均访问深度</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
            </el-card>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="24">
        <el-card shadow="hover" v-for="item in monthlyVisit" :key="item.refDate">
          <div slot="header" class="clearfix">
            <b>小程序月访问趋势(<b>{{item.refDate}}</b>)</b>
            <el-date-picker
              style="float: right;width: 160px"
              v-model="value4"
              type="month"
              format="yyyy-MM"
              @change="changeValue4"
              value-format="yyyy-MM-dd"
              placeholder="选择月"
              :picker-options="pickerMonthOptions">
            </el-date-picker>
          </div>
          <el-form :inline="true">
            <el-card shadow="hover">
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.sessionCnt" class="item">
                    <el-button type="primary" round size="small">打开次数</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.visitPv" class="item">
                    <el-button type="success" round size="small">访问次数</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.visitUv" class="item">
                    <el-button type="warning" round size="small">访问人数</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.visitUvNew" class="item">
                    <el-button type="danger" round size="small">新用户数</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.stayTimeUv" class="item">
                    <el-button type="primary" round size="small">人均停留时长</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.stayTimeSession" class="item">
                    <el-button type="success" round size="small">次均停留时长</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-badge :value="item.visitDepth" class="item">
                    <el-button type="warning" round size="small">平均访问深度</el-button>
                  </el-badge>
                </el-form-item>
              </el-col>
            </el-card>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="24">
        <el-card shadow="hover">
          <div slot="header" class="clearfix">
            <b>小程序访问分布数据(<b>{{visitDistribution.refDate}}</b>)</b>
            <el-date-picker
              style="float: right;width: 140px"
              v-model="value5"
              type="date"
              @change="changeValue5"
              value-format="yyyy-MM-dd"
              placeholder="选择日期"
              :picker-options="pickerOptions">
            </el-date-picker>
          </div>
          <el-form :inline="true">
            <el-col :span="12">
              <el-card shadow="hover">
                <div id="initVisitDistributionCntChart" class="chart-box"></div>
              </el-card>
            </el-col>
            <el-col :span="12">
              <el-card shadow="hover">
                <el-card shadow="hover">
                  <div id="initVisitDistributionUvChart" class="chart-box"></div>
                </el-card>
              </el-card>
            </el-col>
            <el-col :span="12">
              <el-card shadow="hover">
                <el-card shadow="hover">
                  <div id="initVisitDistributionDeepChart" class="chart-box"></div>
                </el-card>
              </el-card>
            </el-col>
            <el-col :span="12">
              <el-card shadow="hover">
                <div id="initVisitDistributionStayTimeChart" class="chart-box"></div>
              </el-card>
            </el-col>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="24">
        <el-card shadow="hover">
          <div slot="header" class="clearfix">
            <b>小程序访问页面排行(<b>{{visitPageList.date}}</b>)</b>
            <el-date-picker
              style="float: right;width: 140px"
              v-model="value6"
              type="date"
              @change="changeValue6"
              value-format="yyyy-MM-dd"
              placeholder="选择日期"
              :picker-options="pickerOptions">
            </el-date-picker>
          </div>
          <el-table
            :data="visitPageList.list"
            height="400"
            border
            style="width: 100%">
            <el-table-column
              type="index"
              :index="indexMethod">
            </el-table-column>
            <el-table-column
              prop="pagePath"
              label="页面路径"
              width="300">
            </el-table-column>
            <el-table-column
              prop="pageVisitPv"
              label="访问次数">
            </el-table-column>
            <el-table-column
              prop="pageVisitUv"
              label="访问人数">
            </el-table-column>
            <el-table-column
              prop="pageStayTimePv"
              label="次均停留时长">
            </el-table-column>
            <el-table-column
              prop="entryPagePv"
              label="进入页次数">
            </el-table-column>
            <el-table-column
              prop="exitPagePv"
              label="退出页次数">
            </el-table-column>
            <el-table-column
              prop="pageSharePv"
              label="转发次数">
            </el-table-column>
            <el-table-column
              prop="pageShareUv"
              label="转发人数">
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="24">
        <el-card shadow="hover">
          <div id="initFootprintCountChart" class="chart-box"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        pickerOptions: {
          disabledDate (time) {
            return time.getTime() > Date.now() - 3600 * 1000 * 24
          },
          shortcuts: [{
            text: '昨天',
            onClick (picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24)
              picker.$emit('pick', date)
            }
          }, {
            text: '前天',
            onClick (picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 2)
              picker.$emit('pick', date)
            }
          }, {
            text: '一周前',
            onClick (picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', date)
            }
          }],
          firstDayOfWeek: 1
        },
        pickerWeekOptions: {
          disabledDate (time) {
            let now = new Date()
            let nowYear = now.getFullYear()
            let nowMonth = now.getMonth()
            let nowDay = now.getDate()
            let day = now.getDay() || 7
            return time.getTime() > new Date(nowYear, nowMonth, nowDay + 1 - day).getTime() - 1
          },
          firstDayOfWeek: 1
        },
        pickerMonthOptions: {
          disabledDate (time) {
            let now = new Date()
            let nowYear = now.getFullYear()
            let nowMonth = now.getMonth()
            return time.getTime() > new Date(nowYear, nowMonth, 1).getTime() - 1
          }
        },
        value1: '',
        value2: '',
        value3: '',
        value4: '',
        value5: '',
        value6: '',
        dailySummary: [],
        dailyVisit: [],
        weeklyVisit: [],
        monthlyVisit: [],
        visitPageList: [],
        visitDistribution: {
          refDate: '',
          list: {
            access_source_visit_uv: {},
            access_staytime_info: {},
            access_source_session_cnt: {},
            access_depth_info: {}
          }
        },
        footprintCountChart: null,
        visitDistributionCntChart: null,
        visitDistributionStayTimeChart: null,
        visitDistributionDeepChart: null
      }
    },
    created () {
      // this.initFootprintCount()
      this.getDailySummaryTrend()
      this.getDailyVisitTrend()
      this.getWeeklyVisitTrend()
      this.getMonthlyVisitTrend()
      this.getVisitDistribution()
      this.getVisitPage()
    },
    activated () {
      // 由于给echart添加了resize事件, 在组件激活时需要重新resize绘画一次, 否则出现空白bug
      if (this.footprintCountChart) {
        this.footprintCountChart.resize()
      }
      if (this.visitDistributionCntChart) {
        this.visitDistributionCntChart.resize()
      }
      if (this.visitDistributionStayTimeChart) {
        this.visitDistributionStayTimeChart.resize()
      }
      if (this.visitDistributionDeepChart) {
        this.visitDistributionDeepChart.resize()
      }
    },
    methods: {
      indexMethod (index) {
        return index + 1
      },
      changeValue1 () {
        this.getDailySummaryTrend()
      },
      changeValue2 () {
        this.getDailyVisitTrend()
      },
      changeValue3 () {
        this.getWeeklyVisitTrend()
      },
      changeValue4 () {
        this.getMonthlyVisitTrend()
      },
      changeValue5 () {
        this.getVisitDistribution()
      },
      changeValue6 () {
        this.getVisitPage()
      },
      getDailySummaryTrend () {
        this.$http({
          url: '/wx/maanalysis/getDailySummaryTrend',
          method: 'get',
          params: {
            date: this.value1
          }
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.dailySummary = data.list
          }
        })
      },
      getDailyVisitTrend () {
        this.$http({
          url: '/wx/maanalysis/getDailyVisitTrend',
          method: 'get',
          params: {
            date: this.value2
          }
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.dailyVisit = data.list
          }
        })
      },
      getWeeklyVisitTrend () {
        this.$http({
          url: '/wx/maanalysis/getWeeklyVisitTrend',
          method: 'get',
          params: {
            date: this.value3
          }
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.weeklyVisit = data.list
          }
        })
      },
      getMonthlyVisitTrend () {
        this.$http({
          url: '/wx/maanalysis/getMonthlyVisitTrend',
          method: 'get',
          params: {
            date: this.value4
          }
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.monthlyVisit = data.list
          }
        })
      },
      getVisitDistribution () {
        this.$http({
          url: '/wx/maanalysis/getVisitDistribution',
          method: 'get',
          params: {
            date: this.value5
          }
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.visitDistribution = data.data

            // 访问来源分布
            let visitDistributionCntChartDataX = []
            let visitDistributionCntChartDataY = []
            for (let key in this.visitDistribution.list.access_source_session_cnt) {
              let element = this.visitDistribution.list.access_source_session_cnt[key]
              visitDistributionCntChartDataX.push(this.translateScene(key))
              visitDistributionCntChartDataY.push({
                value: element,
                name: this.translateScene(key)
              })
            }
            let cntOption = {
              title: {
                text: '访问来源分布（PV）',
                left: 'center'
              },
              tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b}: {c} ({d}%)'
              },
              legend: {
                orient: 'vertical',
                left: 10,
                data: visitDistributionCntChartDataX
              },
              toolbox: {
                show: true,
                feature: {
                  mark: { show: true },
                  dataView: {
                    show: true,
                    readOnly: true
                  },
                  magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                  },
                  saveAsImage: { show: true }
                }
              },
              series: [{
                name: '访问来源(PV)',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                  show: false,
                  position: 'center'
                },
                emphasis: {
                  label: {
                    show: true,
                    fontSize: '20',
                    fontWeight: 'bold'
                  }
                },
                labelLine: {
                  show: false
                },
                data: visitDistributionCntChartDataY
              }]
            }
            this.visitDistributionCntChart = this.$echarts.init(document.getElementById('initVisitDistributionCntChart'))
            this.visitDistributionCntChart.setOption(cntOption)
            window.addEventListener('resize', () => {
              this.visitDistributionCntChart.resize()
            })

            // 访问时长分布UV
            let visitDistributionUvChartDataX = []
            let visitDistributionUvChartDataY = []
            for (let key in this.visitDistribution.list.access_source_visit_uv) {
              let element = this.visitDistribution.list.access_source_visit_uv[key]
              visitDistributionUvChartDataX.push(this.translateScene(key))
              visitDistributionUvChartDataY.push({
                value: element,
                name: this.translateScene(key)
              })
            }
            let uvOption = {
              title: {
                text: '访问来源分布（UV）',
                left: 'center'
              },
              tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b}: {c} ({d}%)'
              },
              legend: {
                orient: 'vertical',
                left: 10,
                data: visitDistributionUvChartDataX
              },
              toolbox: {
                show: true,
                feature: {
                  mark: { show: true },
                  dataView: {
                    show: true,
                    readOnly: true
                  },
                  magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                  },
                  saveAsImage: { show: true }
                }
              },
              series: [{
                name: '访问来源(UV)',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                  show: false,
                  position: 'center'
                },
                emphasis: {
                  label: {
                    show: true,
                    fontSize: '20',
                    fontWeight: 'bold'
                  }
                },
                labelLine: {
                  show: false
                },
                data: visitDistributionUvChartDataY
              }]
            }
            this.visitDistributionUvChart = this.$echarts.init(document.getElementById('initVisitDistributionUvChart'))
            this.visitDistributionUvChart.setOption(uvOption)
            window.addEventListener('resize', () => {
              this.visitDistributionUvChart.resize()
            })

            // 访问深度的分布
            let visitDistributionDeepChartDataX = []
            let visitDistributionDeepChartDataY = []
            for (let key in this.visitDistribution.list.access_depth_info) {
              let element = this.visitDistribution.list.access_depth_info[key]
              visitDistributionDeepChartDataX.push(this.translateDeep(key))
              visitDistributionDeepChartDataY.push(element)
            }
            let deepOption = {
              color: ['#11C26D'],
              title: {
                text: '访问深度',
                left: 'center'
              },
              toolbox: {
                show: true,
                feature: {
                  mark: { show: true },
                  dataView: {
                    show: true,
                    readOnly: true
                  },
                  magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                  },
                  saveAsImage: { show: true }
                }
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'shadow'
                }
              },
              grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
              },
              xAxis: [{
                type: 'category',
                data: visitDistributionDeepChartDataX,
                axisTick: {
                  alignWithLabel: true
                }
              }],
              series: [{
                name: '访问深度(PV)',
                type: 'bar',
                barWidth: '60%',
                data: visitDistributionDeepChartDataY
              }],
              yAxis: {
                type: 'value'
              }
            }
            this.visitDistributionDeepChart = this.$echarts.init(document.getElementById('initVisitDistributionDeepChart'))
            this.visitDistributionDeepChart.setOption(deepOption)
            window.addEventListener('resize', () => {
              this.visitDistributionDeepChart.resize()
            })

            // 访问时长分布
            let visitDistributionStayTimeChartDataX = []
            let visitDistributionStayTimeChartDataY = []
            for (let key in this.visitDistribution.list.access_staytime_info) {
              let element = this.visitDistribution.list.access_staytime_info[key]
              visitDistributionStayTimeChartDataX.push(this.translateStayTime(key))
              visitDistributionStayTimeChartDataY.push(element)
            }
            let stayTimeOption = {
              color: ['#3E8EF7'],
              title: {
                text: '访问时长',
                left: 'center'
              },
              toolbox: {
                show: true,
                feature: {
                  mark: { show: true },
                  dataView: {
                    show: true,
                    readOnly: true
                  },
                  magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                  },
                  saveAsImage: { show: true }
                }
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'shadow'
                }
              },
              grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
              },
              xAxis: [{
                type: 'category',
                data: visitDistributionStayTimeChartDataX,
                axisTick: {
                  alignWithLabel: true
                }
              }],
              series: [{
                name: '访问时长(PV)',
                type: 'bar',
                barWidth: '60%',
                data: visitDistributionStayTimeChartDataY
              }],
              yAxis: {
                type: 'value'
              }
            }
            this.visitDistributionStayTimeChart = this.$echarts.init(document.getElementById('initVisitDistributionStayTimeChart'))
            this.visitDistributionStayTimeChart.setOption(stayTimeOption)
            window.addEventListener('resize', () => {
              this.visitDistributionStayTimeChart.resize()
            })
          }
        })
      },
      getVisitPage () {
        this.$http({
          url: '/wx/maanalysis/getVisitPage',
          method: 'get',
          params: {
            date: this.value6
          }
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.visitPageList = data
          }
        })
      },
      // 柱状图
      // initFootprintCount () {
      //   this.$http({
      //     url: `/mall/footprint/footprintCount`,
      //     method: 'get'
      //   }).then(({ data }) => {
      //     if (data && data.code === 0) {
      //       let list = data.list
      //       let xData = []
      //       let yData = []
      //       for (let item of list) {
      //         xData.push(item.goodsName)
      //         yData.push(item.goodsSum)
      //       }
      //       let option = {
      //         title: {
      //           text: '商品浏览量',
      //           subtext: '统计'
      //         },
      //         tooltip: {
      //           trigger: 'axis',
      //           axisPointer: {            // 坐标轴指示器，坐标轴触发有效
      //             type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
      //           }
      //         },
      //         grid: {
      //           left: '3%',
      //           right: '4%',
      //           bottom: '3%',
      //           containLabel: true
      //         },
      //         xAxis: [
      //           {
      //             type: 'category',
      //             data: xData,
      //             axisTick: {
      //               alignWithLabel: true
      //             }
      //           }
      //         ],
      //         yAxis: [
      //           {
      //             type: 'value'
      //           }
      //         ],
      //         series: [
      //           {
      //             name: '点击量（次）',
      //             type: 'bar',
      //             barWidth: '60%',
      //             data: yData
      //           }
      //         ]
      //       }
      //       this.footprintCountChart = this.$echarts.init(document.getElementById('initFootprintCountChart'))
      //       this.footprintCountChart.setOption(option)
      //       window.addEventListener('resize', () => {
      //         this.footprintCountChart.resize()
      //       })
      //     }
      //   })
      // },
      translateScene (sceneId) {
        let result = ''
        sceneId = Number(sceneId)
        switch (sceneId) {
          case 1:
            result = '小程序历史列表'
            break
          case 2:
            result = '搜索'
            break
          case 3:
            result = '会话'
            break
          case 4:
            result = '扫一扫二维码'
            break
          case 5:
            result = '公众号主页'
            break
          case 6:
            result = '聊天顶部'
            break
          case 7:
            result = '系统桌面'
            break
          case 8:
            result = '小程序主页'
            break
          case 9:
            result = '附近的小程序'
            break
          case 11:
            result = '模板消息'
            break
          case 13:
            result = '公众号菜单'
            break
          case 14:
            result = 'APP分享'
            break
          case 15:
            result = '支付完成页'
            break
          case 16:
            result = '长按识别二维码'
            break
          case 17:
            result = '相册选取二维码'
            break
          case 18:
            result = '公众号文章'
            break
          case 19:
            result = '钱包'
            break
          case 20:
            result = '卡包'
            break
          case 21:
            result = '小程序内卡券'
            break
          case 22:
            result = '其他小程序'
            break
          case 23:
            result = '其他小程序返回'
            break
          case 24:
            result = '卡券适用门店列表'
            break
          case 25:
            result = '搜索框快捷入口'
            break
          case 26:
            result = '小程序客服消息'
            break
          case 27:
            result = '公众号下发'
            break
          case 29:
            result = '任务栏-最近使用'
            break
          case 30:
            result = '长按小程序菜单圆点'
            break
          case 31:
            result = '连wifi成功页'
            break
          case 32:
            result = '城市服务'
            break
          case 33:
            result = '微信广告'
            break
          case 34:
            result = '其他移动应用'
            break
          case 35:
            result = '发现入口-我的小程序'
            break
          case 36:
            result = '任务栏-我的小程序'
            break
          default:
            result = '其他'
        }
        return result
      },
      translateStayTime (key) {
        let result = ''
        key = Number(key)
        switch (key) {
          case 1:
            result = '0-2s'
            break
          case 2:
            result = '3-5s'
            break
          case 3:
            result = '6-10s'
            break
          case 4:
            result = '11-20s'
            break
          case 5:
            result = '20-30s'
            break
          case 6:
            result = '30-50s'
            break
          case 7:
            result = '50-100s'
            break
          case 8:
            result = '>100s'
            break
          default:
            result = '其他'
        }
        return result
      },
      translateDeep (key) {
        let result = ''
        key = Number(key)
        switch (key) {
          case 1:
            result = '1 页'
            break
          case 2:
            result = '2 页'
            break
          case 3:
            result = '3 页'
            break
          case 4:
            result = '4 页'
            break
          case 5:
            result = '5 页'
            break
          case 6:
            result = '6-10 页'
            break
          case 7:
            result = '>10 页'
            break
          default:
            result = '其他'
        }
        return result
      }
    }
  }
</script>

<style lang="scss">
  .chart-box {
    min-height: 400px;
  }
</style>
