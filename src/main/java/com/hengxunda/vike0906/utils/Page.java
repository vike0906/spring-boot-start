package com.hengxunda.vike0906.utils;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.math.NumberUtils;

@Setter
@Getter
@Accessors(chain = true)
public class Page {

    /** 分页默认页 */
    public static final int DEFAULT_PAGE_NO = 1;
    /** 分页默认的每页条数 */
    public static final int DEFAULT_LIMIT = 15;
    /** 前台传递过来的分页参数名 */
    public static final String GLOBAL_PAGE = "page";
    /** 前台传递过来的每页条数名 */
    public static final String GLOBAL_LIMIT = "limit";


    @ApiParam("当前页数, app 调用时此值会被忽略. pc 端调用时不传则默认是 " + DEFAULT_PAGE_NO)
    private int page;

    @ApiParam("每页条数, 不传则默认是 " + DEFAULT_LIMIT)
    private int limit;

    public Page() {
        this.page = 1;
        this.limit = DEFAULT_LIMIT;
    }
    public Page(String page, String limit) {
        int pageNum = NumberUtils.toInt(page);
        if (pageNum <= 0) {
            pageNum = DEFAULT_PAGE_NO;
        }
        this.page = pageNum;

        int limitNum = NumberUtils.toInt(limit);
        if (limitNum <= 0 || limitNum > 1000) {
            limitNum = DEFAULT_LIMIT;
        }
        this.limit = limitNum;
    }

    public PageBounds getAppPageBounds() {
        return new PageBounds(limit);
    }
    public PageBounds getPcPageBounds() {
        return new PageBounds(page, limit);
    }
}
