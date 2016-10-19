<#if (totalPages >1)>
	<div class="pagination">
		<#if isFirst>
			<span class="firstPage">&nbsp;</span>
		<#else>
			<a href="javascript:void(0);" onclick="return pageSkip('${firstPageNumber}');">&nbsp;</a>
		</#if>
		<#if hasPrevious>
			<a href="javascript:void(0);" onclick="return pageSkip('${previousPageNumber}');">&nbsp;</a>
		<#else>
			<span class="previousPage">&nbsp;</span>
		</#if>
		<#list segment as segmentPageNumber>
			<#if segmentPageNumber_index == 0 && (segmentPageNumber > firstPageNumber + 1)>
				<span class="pageBreak">...</span>
			</#if>
			<#if segmentPageNumber != pageNumber>
				<a href="javascript:void(0);" onclick="return pageSkip('${segmentPageNumber}');">${segmentPageNumber}</a>
			<#else>
				<span class="currentPage">${segmentPageNumber}</span>
			</#if>
			<#if !segmentPageNumber_has_next && (segmentPageNumber < lastPageNumber - 1)>
				<span class="pageBreak">...</span>
			</#if>
		</#list>
		<#if hasNext>
			<a href="javascript:void(0);" onclick="return pageSkip('${nextPageNumber}');">&nbsp;</a>
		<#else>
			<span class="nextPage">&nbsp;</span>
		</#if>
		<#if isLast>
			<span class="lastPage">&nbsp;</span>
		<#else>
			<a href="javascript:void(0);" onclick="return pageSkip('${lastPageNumber}');">&nbsp;</a>
		</#if>
	</div>
</#if>