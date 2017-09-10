<?xml version='1.0' encoding='UTF-8'?>
<xsl:stylesheet version='2.0'
	xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>
	<xsl:output method='xml' />
	<xsl:param name='student' />
	<xsl:template match="node()|@*">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>
	</xsl:template>
	<xsl:template match='projectDescription/name'>
		<name>
			<xsl:value-of select='$student' />
		</name>
	</xsl:template>
</xsl:stylesheet>
