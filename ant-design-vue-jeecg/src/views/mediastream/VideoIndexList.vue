<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('tb_video_index')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{fixed:true,selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"

        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <videoIndex-modal ref="modalForm" @ok="modalFormOk"></videoIndex-modal>
  </a-card>
</template>

<script>

    import { JeecgListMixin } from '@/mixins/JeecgListMixin'
    import VideoIndexModal from './modules/VideoIndexModal'

    export default {
        name: 'VideoIndexList',
        mixins: [JeecgListMixin],
        components: {
            VideoIndexModal
        },
        data () {
            return {
                description: 'tb_video_index管理页面',
                // 表头
                columns: [
                    {
                        title: '#',
                        dataIndex: '',
                        key: 'rowIndex',
                        width: 60,
                        align: 'center',
                        customRender: function (t, r, index) {
                            return parseInt(index) + 1
                        }
                    },
                    {
                        title: '创建人',
                        align: 'center',
                        dataIndex: 'createBy'
                    },
                    {
                        title: '创建日期',
                        align: 'center',
                        dataIndex: 'createTime',
                        customRender: function (text) {
                            return !text ? '' : (text.length > 10 ? text.substr(0, 10) : text)
                        }
                    },
                    {
                        title: '更新人',
                        align: 'center',
                        dataIndex: 'updateBy'
                    },
                    {
                        title: '更新日期',
                        align: 'center',
                        dataIndex: 'updateTime',
                        customRender: function (text) {
                            return !text ? '' : (text.length > 10 ? text.substr(0, 10) : text)
                        }
                    },
                    {
                        title: '年代',
                        align: 'center',
                        dataIndex: 'name'
                    },
                    {
                        title: '季度',
                        align: 'center',
                        dataIndex: 'season'
                    },
                    {
                        title: '总集数',
                        align: 'center',
                        dataIndex: 'allChapterNum'
                    },
                    {
                        title: '包含视频数量',
                        align: 'center',
                        dataIndex: 'fileCnt'
                    },
                    {
                        title: '年代',
                        align: 'center',
                        dataIndex: 'year'
                    },
                    {
                        title: '初版名称',
                        align: 'center',
                        dataIndex: 'sourName'
                    },
                    {
                        title: '初版id',
                        align: 'center',
                        dataIndex: 'sourId'
                    },
                    {
                        title: '标签',
                        align: 'center',
                        dataIndex: 'tag'
                    },
                    {
                        title: '操作',
                        dataIndex: 'action',
                        align: 'center',
                        scopedSlots: { customRender: 'action' }
                    }
                ],
                url: {
                    list: '/mediastream/videoIndex/list',
                    delete: '/mediastream/videoIndex/delete',
                    deleteBatch: '/mediastream/videoIndex/deleteBatch',
                    exportXlsUrl: '/mediastream/videoIndex/exportXls',
                    importExcelUrl: 'mediastream/videoIndex/importExcel'
                },
                dictOptions: {}
            }
        },
        computed: {
            importExcelUrl: function () {
                return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
            }
        },
        methods: {
            initDictConfig () {
            }
        }
    }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>