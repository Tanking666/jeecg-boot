package org.jeecg.modules.buss.mediastream.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: tb_video_index
 * @Author: jeecg-boot
 * @Date:   2020-03-25
 * @Version: V1.0
 */
@Data
@TableName("tb_video_index")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_video_index对象", description="tb_video_index")
public class VideoIndex implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "id")
    private String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**年代*/
	@Excel(name = "年代", width = 15)
    @ApiModelProperty(value = "年代")
    private String name;
	/**季度*/
	@Excel(name = "季度", width = 15)
    @ApiModelProperty(value = "季度")
    private String season;
	/**总集数*/
	@Excel(name = "总集数", width = 15)
    @ApiModelProperty(value = "总集数")
    private Integer allChapterNum;
	/**包含视频数量*/
	@Excel(name = "包含视频数量", width = 15)
    @ApiModelProperty(value = "包含视频数量")
    private Integer fileCnt;
	/**年代*/
	@Excel(name = "年代", width = 15)
    @ApiModelProperty(value = "年代")
    private String year;
	/**初版名称*/
	@Excel(name = "初版名称", width = 15)
    @ApiModelProperty(value = "初版名称")
    private String sourName;
	/**初版id*/
	@Excel(name = "初版id", width = 15)
    @ApiModelProperty(value = "初版id")
    private String sourId;
	/**标签*/
	@Excel(name = "标签", width = 15)
    @ApiModelProperty(value = "标签")
    private String tag;
}
