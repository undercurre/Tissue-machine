<template>
  <el-dialog
    title="纸巾机管理后台数据分析页面"
    :visible.sync="visible"
    append-to-body
    fullscreen
    :show-close="false"
    :close-on-click-modal="false"
    class="Fullpage"
    destroy-on-close
    :append-to-body="true"
  >
    <div @click="visible = false" class="closeBtn">&times</div>
    <div class="pageForm">
      <div class="pageHead">
        <img src="@/assets/img/capbg.png" class="headBg">
        <h5>纸巾机管理后台数据分析平台</h5>
        <p>佛山市乐晟网络科技有限公司</p>
      </div>
<!--      <div class="countMembers">-->
<!--        <time-count v-bind:number="attendNumber" ></time-count>-->
<!--        <div class="number-box">-->
<!--          <p class="box-item" v-for="(item, index) in computeNumber" :key="index">-->
<!--            <span ref="numberDom">0<br/>1<br/>2<br/>3<br/>4<br/>5<br/>6<br/>7<br/>8<br/>9</span>-->
<!--          </p>-->
<!--        </div>-->
<!--      </div>-->
      <div id="J_chartBarBox01" class="chart-box"></div>
      <div id="J_chartBarBox02" class="chart-box"></div>
      <div id="J_chartBarBox03" class="chart-box"></div>
      <div id="J_chartBarBox04" class="chart-box"></div>
      <div id="J_chartBarBox05" class="chart-box"></div>
      <div id="J_chartBarBox06" class="chart-box"></div>

<!--      <div id="J_chartLineBox" class="chart-box"></div>-->
<!--      <div id="J_chartBarBox" class="chart-box"></div>-->
<!--      <div id="J_chartPieBox" class="chart-box"></div>-->
    </div>
  </el-dialog>
</template>

<script>
import echarts from 'echarts'
export default {
  data () {
    return {
      visible: false,
      chartLine: null,
      chartBar: null,
      chartPie: null,
      maxLen: 8,
      computeNumber: [],
      attendNumber: 1800,
      timeTicket: null
    }
  },
  methods: {
    // 初始化
    init () {
      this.visible = true
      this.$nextTick(() => {
        // this.initChartLine()
        this.initChartBar01()
        this.initChartBar02()
        this.initChartBar03()
        this.initChartBar04()
        this.initChartBar05()
        this.initChartBar06()
        // this.initChartPie()
        // this.chartLine.resize()
        this.chartBar01.resize()
        this.chartBar02.resize()
        this.chartBar03.resize()
        this.chartBar04.resize()
        this.chartBar05.resize()
        this.chartBar06.resize()
        // this.chartPie.resize()
      })
    },
    // 折线图
    initChartLine () {
      var option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['邮件营销'],
          textStyle: {
            fontSize: 13,
            color: '#fff'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        calculable: true,
        xAxis: {
          type: 'category',
          boundaryGap: false,
          axisTick: {
            alignWithLabel: true
          },
          axisLabel: {
            color: '#fff',
            fontSize: 10
          },
          axisLine: {
            lineStyle: {
              color: '#102038'
            }
          },
          data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        },
        yAxis: {
          type: 'value',
          axisLine: {
            show: false
          },
          max: function (value) {
            return value.max + 2
          },
          axisTick: {
            show: false
          },
          splitLine: {
            lineStyle: {
              color: '#fff'
            }
          },
          axisLabel: {
            color: '#fff',
            fontSize: 10
          },
          nameLocation: 'center',
          nameGap: 16,
          nameTextStyle: {
            fontSize: 8,
            color: '#EFEEEE'
          }
        },
        series: [{
          name: '邮件营销',
          type: 'line',
          data: [120, 132, 101, 134, 90, 230, 210],
          stack: '总量',
          barWidth: '60%',
          symbol: 'none',
          smooth: 0.2,
          color: ['#66AEDE'],
          itemStyle: {
            normal: {
              barBorderRadius: 2,
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: '#59EFFE'
              }, {
                offset: 1,
                color: '#6664F1'
              }]),
              shadowColor: 'rgba(102, 100, 241, 1)',
              shadowBlur: 5,
              label: {
                show: 'true',
                position: 'top',
                color: '#CCCCCC',
                fontSize: 9
              }
            }
          }
        }]
      }
      this.chartLine = echarts.init(document.getElementById('J_chartLineBox'))
      this.chartLine.setOption(option)
      window.addEventListener('resize', () => {
        this.chartLine.resize()
      })
    },
    // 柱状图
    initChartBar01 () {
      var option = {
        title: {
          text: '广告类',
          textStyle: {
            color: 'red'
          },
          padding: [0, 0, 10, 10]  // 位置
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['广告类数据展示'],
          textStyle: {
            fontSize: 13,
            color: '#fff'
          }
        },
        grid: {
          show: false,
          left: 10,
          right: 0,
          bottom: 10,
          containLabel: true
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            color: '#fff',
            fontSize: 10
          },
          axisLine: {
            lineStyle: {
              color: '#102038'
            }
          },
          data: ['提交意向数量', '已回访数量', '广告类型与数量']
        },
        yAxis: [
          {
            type: 'value',
            axisLine: {
              show: false
            },
            max: function (value) {
              return value.max + 2
            },
            axisTick: {
              show: false
            },
            splitLine: {
              lineStyle: {
                color: '#fff'
              }
            },
            axisLabel: {
              color: '#fff',
              fontSize: 10
            },
            nameLocation: 'center',
            nameGap: 16,
            nameTextStyle: {
              fontSize: 8,
              color: '#EFEEEE'
            }
          }
        ],
        series: [
          {
            name: '广告类数据展示',
            type: 'bar',
            data: [320, 332, 301],
            stack: '总量',
            barWidth: '60%',
            symbol: 'none',
            smooth: 0.2,
            color: ['#66AEDE'],
            itemStyle: {
              normal: {
                barBorderRadius: 2,
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#59EFFE'
                }, {
                  offset: 1,
                  color: '#6664F1'
                }]),
                shadowColor: 'rgba(102, 100, 241, 1)',
                shadowBlur: 5,
                label: {
                  show: 'true',
                  position: 'top',
                  color: '#CCCCCC',
                  fontSize: 9
                }
              }
            }
          }
        ]
      }
      this.chartBar01 = echarts.init(document.getElementById('J_chartBarBox01'))
      this.chartBar01.setOption(option)
      window.addEventListener('resize', () => {
        this.chartBar01.resize()
      })
    },
    initChartBar02 () {
      var option = {
        title: {
          text: '设备类',
          textStyle: {
            color: 'red'
          },
          padding: [0, 0, 10, 10]  // 位置
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['设备类数据展示'],
          textStyle: {
            fontSize: 13,
            color: '#fff'
          }
        },
        grid: {
          show: false,
          left: 10,
          right: 0,
          bottom: 10,
          containLabel: true
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            color: '#fff',
            fontSize: 10
          },
          axisLine: {
            lineStyle: {
              color: '#102038'
            }
          },
          data: ['设备投放总数量', '设备地区分布与数量(市)', '设备地区分布与数量(区)', '设备地区分布与数量(街道)', '在线设备数量', '异常设备数量']
        },
        yAxis: [
          {
            type: 'value',
            axisLine: {
              show: false
            },
            max: function (value) {
              return value.max + 2
            },
            axisTick: {
              show: false
            },
            splitLine: {
              lineStyle: {
                color: '#fff'
              }
            },
            axisLabel: {
              color: '#fff',
              fontSize: 10
            },
            nameLocation: 'center',
            nameGap: 16,
            nameTextStyle: {
              fontSize: 8,
              color: '#EFEEEE'
            }
          }
        ],
        series: [
          {
            name: '设备类数据展示',
            type: 'bar',
            data: [320, 332, 301, 334, 390, 330],
            stack: '总量',
            barWidth: '60%',
            symbol: 'none',
            smooth: 0.2,
            color: ['#66AEDE'],
            itemStyle: {
              normal: {
                barBorderRadius: 2,
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#59EFFE'
                }, {
                  offset: 1,
                  color: '#6664F1'
                }]),
                shadowColor: 'rgba(102, 100, 241, 1)',
                shadowBlur: 5,
                label: {
                  show: 'true',
                  position: 'top',
                  color: '#CCCCCC',
                  fontSize: 9
                }
              }
            }
          }
        ]
      }
      this.chartBar02 = echarts.init(document.getElementById('J_chartBarBox02'))
      this.chartBar02.setOption(option)
      window.addEventListener('resize', () => {
        this.chartBar02.resize()
      })
    },
    initChartBar03 () {
      var option = {
        title: {
          text: '纸巾类',
          textStyle: {
            color: 'red'
          },
          padding: [0, 0, 10, 10]  // 位置
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['纸巾类数据展示'],
          textStyle: {
            fontSize: 13,
            color: '#fff'
          }
        },
        grid: {
          show: false,
          left: 10,
          right: 0,
          bottom: 10,
          containLabel: true
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            color: '#fff',
            fontSize: 10
          },
          axisLine: {
            lineStyle: {
              color: '#102038'
            }
          },
          data: [
            '纸巾库存总数量',
            '纸巾总派发数量',
            '纸巾本年度派发量',
            '纸巾本季度派发量',
            '纸巾本月派发量',
            '纸巾本周派发量',
            '纸巾今日派发量',
            '纸巾人均本年度派发量',
            '纸巾人均本季度派发量',
            '纸巾人均本月派发量',
            '纸巾人均本周派发量',
            '纸巾人均今日派发量',
            '地区总派发排行（市排行）',
            '地区总派发排行（区排行）',
            '地区总派发排行（街道排行）'
          ]
        },
        yAxis: [
          {
            type: 'value',
            axisLine: {
              show: false
            },
            max: function (value) {
              return value.max + 2
            },
            axisTick: {
              show: false
            },
            splitLine: {
              lineStyle: {
                color: '#fff'
              }
            },
            axisLabel: {
              color: '#fff',
              fontSize: 10
            },
            nameLocation: 'center',
            nameGap: 16,
            nameTextStyle: {
              fontSize: 8,
              color: '#EFEEEE'
            }
          }
        ],
        series: [
          {
            name: '纸巾类数据展示',
            type: 'bar',
            data: [320, 332, 301, 334, 390, 330, 320, 320, 332, 301, 334, 390, 330, 320, 310],
            stack: '总量',
            barWidth: '60%',
            symbol: 'none',
            smooth: 0.2,
            color: ['#66AEDE'],
            itemStyle: {
              normal: {
                barBorderRadius: 2,
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#59EFFE'
                }, {
                  offset: 1,
                  color: '#6664F1'
                }]),
                shadowColor: 'rgba(102, 100, 241, 1)',
                shadowBlur: 5,
                label: {
                  show: 'true',
                  position: 'top',
                  color: '#CCCCCC',
                  fontSize: 9
                }
              }
            }
          }
        ]
      }
      this.chartBar03 = echarts.init(document.getElementById('J_chartBarBox03'))
      this.chartBar03.setOption(option)
      window.addEventListener('resize', () => {
        this.chartBar03.resize()
      })
    },
    initChartBar04 () {
      var option = {
        title: {
          text: '用户类',
          textStyle: {
            color: 'red'
          },
          padding: [0, 0, 10, 10]  // 位置
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['用户类数据展示'],
          textStyle: {
            fontSize: 13,
            color: '#fff'
          }
        },
        grid: {
          show: false,
          left: 10,
          right: 0,
          bottom: 10,
          containLabel: true
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            color: '#fff',
            fontSize: 10
          },
          axisLine: {
            lineStyle: {
              color: '#102038'
            }
          },
          data: [
            '平台用户注册总数',
            '用户注册地区分布与数量（市、区、街道，根据用户授权登录记录位置）',
            '本年度用户注册数量',
            '本季度用户注册数量',
            '本月用户注册数量',
            '本周用户注册数量',
            '今日用户注册数量',
            '用户男女比例'
          ]
        },
        yAxis: [
          {
            type: 'value',
            axisLine: {
              show: false
            },
            max: function (value) {
              return value.max + 2
            },
            axisTick: {
              show: false
            },
            splitLine: {
              lineStyle: {
                color: '#fff'
              }
            },
            axisLabel: {
              color: '#fff',
              fontSize: 10
            },
            nameLocation: 'center',
            nameGap: 16,
            nameTextStyle: {
              fontSize: 8,
              color: '#EFEEEE'
            }
          }
        ],
        series: [
          {
            name: '用户类数据展示',
            type: 'bar',
            data: [320, 332, 301, 334, 390, 330, 320, 320],
            stack: '总量',
            barWidth: '60%',
            symbol: 'none',
            smooth: 0.2,
            color: ['#66AEDE'],
            itemStyle: {
              normal: {
                barBorderRadius: 2,
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#59EFFE'
                }, {
                  offset: 1,
                  color: '#6664F1'
                }]),
                shadowColor: 'rgba(102, 100, 241, 1)',
                shadowBlur: 5,
                label: {
                  show: 'true',
                  position: 'top',
                  color: '#CCCCCC',
                  fontSize: 9
                }
              }
            }
          }
        ]
      }
      this.chartBar04 = echarts.init(document.getElementById('J_chartBarBox04'))
      this.chartBar04.setOption(option)
      window.addEventListener('resize', () => {
        this.chartBar04.resize()
      })
    },
    initChartBar05 () {
      var option = {
        title: {
          text: '会员类',
          textStyle: {
            color: 'red'
          },
          padding: [0, 0, 10, 10]  // 位置
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['会员类数据展示'],
          textStyle: {
            fontSize: 13,
            color: '#fff'
          }
        },
        grid: {
          show: false,
          left: 10,
          right: 0,
          bottom: 10,
          containLabel: true
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            color: '#fff',
            fontSize: 10
          },
          axisLine: {
            lineStyle: {
              color: '#102038'
            }
          },
          data: [
            '平台激活会员总数',
            '平台激活会员比例（注册数量与实际激活会员卡数量）',
            '本年度用户激活会员数量',
            '本季度用户激活会员数量',
            '本月用户激活会员数量',
            '本周用户激活会员数量',
            '今日用户激活会员数量',
            '会员男女比例',
            '会员用户待领取纸巾总数（用户纸巾余额）'
          ]
        },
        yAxis: [
          {
            type: 'value',
            axisLine: {
              show: false
            },
            max: function (value) {
              return value.max + 2
            },
            axisTick: {
              show: false
            },
            splitLine: {
              lineStyle: {
                color: '#fff'
              }
            },
            axisLabel: {
              color: '#fff',
              fontSize: 10
            },
            nameLocation: 'center',
            nameGap: 16,
            nameTextStyle: {
              fontSize: 8,
              color: '#EFEEEE'
            }
          }
        ],
        series: [
          {
            name: '会员类数据展示',
            type: 'bar',
            data: [320, 332, 301, 334, 390, 330, 320, 330, 320],
            stack: '总量',
            barWidth: '60%',
            symbol: 'none',
            smooth: 0.2,
            color: ['#66AEDE'],
            itemStyle: {
              normal: {
                barBorderRadius: 2,
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#59EFFE'
                }, {
                  offset: 1,
                  color: '#6664F1'
                }]),
                shadowColor: 'rgba(102, 100, 241, 1)',
                shadowBlur: 5,
                label: {
                  show: 'true',
                  position: 'top',
                  color: '#CCCCCC',
                  fontSize: 9
                }
              }
            }
          }
        ]
      }
      this.chartBar05 = echarts.init(document.getElementById('J_chartBarBox05'))
      this.chartBar05.setOption(option)
      window.addEventListener('resize', () => {
        this.chartBar05.resize()
      })
    },
    initChartBar06 () {
      var option = {
        title: {
          text: '小程序类',
          textStyle: {
            color: 'red'
          },
          padding: [0, 0, 10, 10]  // 位置
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['小程序类数据展示'],
          textStyle: {
            fontSize: 13,
            color: '#fff'
          }
        },
        grid: {
          show: false,
          left: 10,
          right: 0,
          bottom: 10,
          containLabel: true
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            color: '#fff',
            fontSize: 10
          },
          axisLine: {
            lineStyle: {
              color: '#102038'
            }
          },
          data: [
            '小程序本年度使用量（打开小程序次数）',
            '小程序本季度使用量',
            '小程序本月使用量',
            '小程序本周使用量',
            '小程序今日使用量'
          ]
        },
        yAxis: [
          {
            type: 'value',
            axisLine: {
              show: false
            },
            max: function (value) {
              return value.max + 2
            },
            axisTick: {
              show: false
            },
            splitLine: {
              lineStyle: {
                color: '#fff'
              }
            },
            axisLabel: {
              color: '#fff',
              fontSize: 10
            },
            nameLocation: 'center',
            nameGap: 16,
            nameTextStyle: {
              fontSize: 8,
              color: '#EFEEEE'
            }
          }
        ],
        series: [
          {
            name: '小程序类数据展示',
            type: 'bar',
            data: [320, 332, 301, 334, 390],
            stack: '总量',
            barWidth: '60%',
            symbol: 'none',
            smooth: 0.2,
            color: ['#66AEDE'],
            itemStyle: {
              normal: {
                barBorderRadius: 2,
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#59EFFE'
                }, {
                  offset: 1,
                  color: '#6664F1'
                }]),
                shadowColor: 'rgba(102, 100, 241, 1)',
                shadowBlur: 5,
                label: {
                  show: 'true',
                  position: 'top',
                  color: '#CCCCCC',
                  fontSize: 9
                }
              }
            }
          }
        ]
      }
      this.chartBar06 = echarts.init(document.getElementById('J_chartBarBox06'))
      this.chartBar06.setOption(option)
      window.addEventListener('resize', () => {
        this.chartBar06.resize()
      })
    },
    // 饼状图
    initChartPie () {
      var option = {
        color: ['#6563F1', '#5CA3F6', '#6EC0FF', '#6fa0c6', '#6fa041'],
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        series: [
          {
            name: '报警类型',
            type: 'pie',
            radius: ['40%', '60%'],
            center: ['50%', '50%'],
            avoidLabelOverlap: true,
            label: {
              normal: {
                show: false
              }
            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data: [
              {
                value: 335,
                name: '直接访问',
                itemStyle: {
                  normal: {
                    shadowColor: '#6563F1',
                    shadowBlur: 9
                  }
                }
              },
              {
                value: 310,
                name: '邮件营销',
                itemStyle: {
                  normal: {
                    shadowColor: '#5CA3F6',
                    shadowBlur: 9
                  }
                }
              },
              {
                value: 274,
                name: '联盟广告',
                itemStyle: {
                  normal: {
                    shadowColor: '#6EC0FF',
                    shadowBlur: 9
                  }
                }
              },
              {
                value: 235,
                name: '视频广告',
                itemStyle: {
                  normal: {
                    shadowColor: '#6fa0c6',
                    shadowBlur: 9
                  }
                }
              },
              {
                value: 400,
                name: '搜索引擎',
                itemStyle: {
                  normal: {
                    shadowColor: '#6fa041',
                    shadowBlur: 9
                  }
                }
              }
            ]
          }
        ]
      }
      this.chartPie = echarts.init(document.getElementById('J_chartPieBox'))
      this.chartPie.setOption(option)
      window.addEventListener('resize', () => {
        this.chartPie.resize()
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.Fullpage /deep/ {
  .el-dialog {
    background-color: rgba(1, 8, 47, 1);
    .el-dialog__header {
      display: none;
      opacity: 0;
    }
    .el-dialog__body {
      overflow: hidden;
      height: 100vh;
      padding: 0;
      .closeBtn {
        background-color: transparent;
        color: #fff;
        font-size: 20px;
        width: 40px;
        height: 40px;
        line-height: 40px;
        text-align: center;
        position: fixed;
        top: 10px;
        right: 10px;
        border-radius: 50%;
        -webkit-border-radius: 50%;
        -moz-border-radius: 50%;
        cursor: pointer;
        transition: all 0.5s;
        z-index: 9999999;
        &:hover {
          background-color: #fff;
          color: #333;
        }
      }
      .pageForm {
        position: relative;
        overflow: hidden;
        height: 100vh;
        padding: 0 15px;
        .pageHead {
          position: relative;
          overflow: hidden;
          h5,p {
            font-size: 22px;
            text-align: center;
            color: #fff;
            position: absolute;
            z-index: 1;
            font-weight: 400;
            padding: 30px 0 0;
            margin: 0;
            width: 100%;
            top: 0;
          }
          p {
            font-size: 14px;
            padding: 40px 0 0;
            top: 60px;
          }
          .headBg {
            width: 600px;
            display: block;
            position: relative;
            margin: 0 auto;
          }
        }
        .chart-box {
          position: relative;
          background: transparent;
          width: 32%;
          margin: 0 2% 30px 0;
          float: left;
          &:nth-of-type(3n) {
            margin: 0 0 30px 0;
          }
        }

        .countMembers {
          position: relative;
          height: 60px;
          .number-item {
            width: 50px;
            background-size:100% 100%;
            & > span {
              position: relative;
              display: inline-block;
              margin-right: 10px;
              width: 100%;
              height: 100%;
              writing-mode: vertical-rl;
              text-orientation: upright;
              overflow: hidden;
              & > i {
                position: absolute;
                top: 0;
                left: 50%;
                transform: translate(-50%,0);
                transition: transform 0.5s ease-in-out;
                letter-spacing: 10px;
              }
            }
          }
        }
      }
    }
    .el-dialog__footer {
      display: none;
      opacity: 0;
    }
  }
}
</style>
