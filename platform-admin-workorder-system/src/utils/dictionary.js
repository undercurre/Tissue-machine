/**
 * 数据转换字典
 */
let orderStatusDic,
  levelDic,
  orderTypeDic,
  statusDic,
  assetsTypeDic,
  maintenancePlanDic,
  deviceStatusDic,
  messageTypeDic

// 状态
statusDic = ['正常', '停用']

orderStatusDic = [
  '待指派',
  '待处理',
  '处理中',
  '等待配件',
  '待返工',
  '待结单',
  '已结单',
  '已作废',
  '委外维修'
]

levelDic = ['普通', '优先', '紧急']

orderTypeDic = ['维修', '保养']

assetsTypeDic = ['固定资产', '非固定资产']

maintenancePlanDic = ['进行中', '已停用']

deviceStatusDic = ['正常运行', '待维修', '维修中', '待保养', '保养中', '已停用']

messageTypeDic = ['设备消息', '工单消息']

export default {
  orderStatusDic,
  levelDic,
  orderTypeDic,
  statusDic,
  assetsTypeDic,
  maintenancePlanDic,
  deviceStatusDic,
  messageTypeDic
}
