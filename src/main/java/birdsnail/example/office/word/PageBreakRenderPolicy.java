package birdsnail.example.office.word;

import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * 添加分节符插件
 */
public class PageBreakRenderPolicy extends AbstractRenderPolicy<Boolean> {

    @Override
    public void doRender(RenderContext<Boolean> context) throws Exception {
        XWPFRun where = context.getWhere();
        boolean thing = context.getThing();
        if (thing) {
            where.addBreak(BreakType.PAGE);
        }
    }

    @Override
    protected void afterRender(RenderContext<Boolean> context) {
        clearPlaceholder(context, false);
    }
}
