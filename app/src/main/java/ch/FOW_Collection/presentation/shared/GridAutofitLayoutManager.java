package ch.FOW_Collection.presentation.shared;

import android.content.Context;
import android.util.TypedValue;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * User: s.mask
 * Date: 2015-05-15
 * Source: https://stackoverflow.com/questions/26666143/recyclerview-gridlayoutmanager-how-to-auto-detect-span-count
 */
public class GridAutofitLayoutManager extends GridLayoutManager
{
    private int mColumnWidth;
    private boolean mColumnWidthChanged = true;

    public GridAutofitLayoutManager(Context context, int columnWidth)
    {
        /* Initially set spanCount to 1, will be changed automatically later. */
        super(context, 1);
        setColumnWidth(checkedColumnWidth(context, columnWidth));
    }

    public GridAutofitLayoutManager(Context context, int columnWidth, int orientation, boolean reverseLayout)
    {
        /* Initially set spanCount to 1, will be changed automatically later. */
        super(context, 1, orientation, reverseLayout);
        setColumnWidth(checkedColumnWidth(context, columnWidth));
    }

    private int checkedColumnWidth(Context context, int columnWidth)
    {
        if (columnWidth <= 0)
        {
            /* Set default columnWidth value (48dp here). It is better to move this constant
            to static constant on top, but we need context to convert it to dp, so can't really
            do so. */
            columnWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48,
                    context.getResources().getDisplayMetrics());
        }
        return columnWidth;
    }

    public void setColumnWidth(int newColumnWidth)
    {
        if (newColumnWidth > 0 && newColumnWidth != mColumnWidth)
        {
            mColumnWidth = newColumnWidth;
            mColumnWidthChanged = true;
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state)
    {
        int width = getWidth();
        int height = getHeight();
        // Have removed check if already calced because of rotation...
        if (mColumnWidth > 0 && width > 0 && height > 0) // mColumnWidthChanged
        {
            int totalSpace;
            if (getOrientation() == RecyclerView.VERTICAL)
            {
                totalSpace = width - getPaddingRight() - getPaddingLeft();
            }
            else
            {
                totalSpace = height - getPaddingTop() - getPaddingBottom();
            }

            int spanCount = Math.max(1, totalSpace / mColumnWidth);
            if (getSpanCount() != spanCount) {
                setSpanCount(spanCount);
            }

            mColumnWidthChanged = false;
        }

        super.onLayoutChildren(recycler, state);
    }
}
