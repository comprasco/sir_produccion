<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
   xmlns:fi="http://apache.org/cocoon/forms/1.0#instance"
   exclude-result-prefixes="fi" 
>
<xsl:output omit-xml-declaration="yes"/>


  <!--+
      | Generic fi:field : produce an <input>
      +-->
  <xsl:template match="fi:field">
    <input name="{@id}" id="{@id}" value="{fi:value}" title="{fi:hint}" type="text">
      <xsl:apply-templates select="." mode="styling"/>
    </input>
    <xsl:apply-templates select="." mode="common"/>
  </xsl:template>


  <!--+
      | Common stuff like fi:validation-message, @required.
      +-->
  <xsl:template match="fi:*" mode="common">
    <!-- validation message -->
    <!--
    <xsl:apply-templates select="fi:validation-message"/>
    -->
    <!-- required mark -->
    <!--
    <xsl:if test="@required='true'">
      <span class="forms-field-required"> * </span>
    </xsl:if>
    -->
  </xsl:template>

  <!--+
      | Handling the common styling. You may only add attributes to the output
      | in this template as later processing might add attributes too, for
      | example @checked or @selected
      +-->
  <xsl:template match="fi:*" mode="styling">
    <xsl:apply-templates select="fi:styling/@*" mode="styling"/>

    <!--+ 
        | @listbox-size needs to be handled separately as even if it is not
        | specified some output (@size) must be generated.
        +-->
    <xsl:if test="self::fi:field[fi:selection-list][fi:styling/@list-type = 'listbox'] or
                  self::fi:multivaluefield[not(fi:styling/@list-type = 'checkbox')]">
      <xsl:variable name="size">
        <xsl:value-of select="fi:styling/@listbox-size"/>
        <xsl:if test="not(fi:styling/@listbox-size)">5</xsl:if>
      </xsl:variable>
      <xsl:attribute name="size">
        <xsl:value-of select="$size"/>
      </xsl:attribute>
    </xsl:if>
  </xsl:template>
  
  
  <xsl:template match="fi:styling/@*" mode="styling">
    <xsl:copy-of select="."/>
  </xsl:template>
  
  <xsl:template match="fi:styling/@list-type | fi:styling/@list-orientation |
                       fi:styling/@listbox-size | fi:styling/@format | fi:styling/@layout"
                mode="styling">
    <!--+
        | Ignore marker attributes so they don't go into the resuling HTML.
        +-->
  </xsl:template>
  
  <xsl:template match="fi:styling/@type" mode="styling">
    <!--+ 
        | Do we have a duplicate semantic usage of @type?
        | @type is only a marker for the stylesheet in general, but some of the
        | types must/should be in the HTML output too.
        +-->
    <xsl:variable name="validHTMLTypes"
                  select="'text hidden textarea checkbox radio password image reset submit'"/>
    <xsl:if test="normalize-space(.) and
                  contains(concat(' ', $validHTMLTypes, ' '), concat(' ', ., ' '))">
      <xsl:copy-of select="."/>
    </xsl:if>
  </xsl:template>
  
    <!--+
      |
      +-->
  <xsl:template match="fi:validation-message">
    <a href="#" class="forms-validation-message" onclick="alert('{normalize-space(.)}');return false;">&#160;!&#160;</a>
  </xsl:template>


  <!--+
      | fi:field with a selection list and @list-type 'radio' : produce
      | radio-buttons oriented according to @list-orientation
      | ("horizontal" or "vertical" - default)
      +-->
  <xsl:template match="fi:field[fi:selection-list][fi:styling/@list-type='radio']" priority="2">
    <xsl:variable name="id" select="@id"/>
    <xsl:variable name="value" select="fi:value"/>
    <xsl:variable name="vertical" select="string(fi:styling/@list-orientation) != 'horizontal'"/>
    <xsl:choose>
      <xsl:when test="$vertical">
        <table cellpadding="0" cellspacing="0" border="0" title="{fi:hint}">
          <xsl:for-each select="fi:selection-list/fi:item">
            <tr>
              <td>
                <input type="radio" id="{generate-id()}" name="{$id}" value="{@value}">
                  <xsl:if test="@value = $value">
                    <xsl:attribute name="checked">checked</xsl:attribute>
                  </xsl:if>
                  <xsl:apply-templates select="../.." mode="styling"/>
                </input>
              </td>
              <td>
                <xsl:apply-templates select="." mode="label">
                  <xsl:with-param name="id" select="generate-id()"/>
                </xsl:apply-templates>
              </td>
              <xsl:if test="position() = 1">
                <td rowspan="{count(../fi:item)}">
                  <xsl:apply-templates select="../.." mode="common"/>
                </td>
              </xsl:if>
            </tr>
          </xsl:for-each>
        </table>
      </xsl:when>
      <xsl:otherwise>
        <span title="{fi:hint}">
          <xsl:for-each select="fi:selection-list/fi:item">
            <input type="radio" id="{generate-id()}" name="{$id}" value="{@value}">
              <xsl:if test="@value = $value">
                <xsl:attribute name="checked">checked</xsl:attribute>
              </xsl:if>
              <xsl:apply-templates select="../.." mode="styling"/>
            </input>
            <xsl:apply-templates select="." mode="label">
              <xsl:with-param name="id" select="generate-id()"/>
            </xsl:apply-templates>
          </xsl:for-each>
        </span>
        <xsl:apply-templates select="." mode="common"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!--+
      | Hidden fi:field : produce input with type='hidden'
      +-->
  <xsl:template match="fi:field[fi:styling/@type='hidden']" priority="2">
    <input type="hidden" name="{@id}" id="{@id}" value="{fi:value}">
      <xsl:apply-templates select="." mode="styling"/>
    </input>
  </xsl:template>

  <!--+
      | fi:field with a selection list (not 'radio' style)
      | Rendering depends on the attributes of fi:styling :
      | - if @list-type is "listbox" : produce a list box with @listbox-size visible
      |   items (default 5)
      | - otherwise, produce a dropdown menu
      +-->
  <xsl:template match="fi:field[fi:selection-list]" priority="1">
    <xsl:variable name="value" select="fi:value"/>

    <!-- dropdown or listbox -->
    <select title="{fi:hint}" id="{@id}" name="{@id}">
      <xsl:apply-templates select="." mode="styling"/>
      <xsl:for-each select="fi:selection-list/fi:item">
        <option value="{@value}">
          <xsl:if test="@value = $value">
            <xsl:attribute name="selected">selected</xsl:attribute>
          </xsl:if>
          <xsl:copy-of select="fi:label/node()"/>
        </option>
      </xsl:for-each>
    </select>
    <xsl:apply-templates select="." mode="common"/>
  </xsl:template>

  <!--+
      | fi:field with a selection list and @type 'output'
      +-->
  <xsl:template match="fi:field[fi:selection-list][fi:styling/@type='output']" priority="3">
    <xsl:variable name="value" select="fi:value"/>
    <xsl:variable name="selected" select="fi:selection-list/fi:item[@value = $value]"/>
    <xsl:choose>
      <xsl:when test="$selected/fi:label">
        <xsl:apply-templates select="$selected/fi:label"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$value"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!--+
      | fi:field with @type 'textarea'
      +-->
  <xsl:template match="fi:field[fi:styling/@type='textarea']">
    <textarea id="{@id}" name="{@id}" title="{fi:hint}">
      <xsl:apply-templates select="." mode="styling"/>
      <!-- remove carriage-returns (occurs on certain versions of IE and doubles linebreaks at each submit) -->
      <xsl:copy-of select="translate(fi:value/node(), '&#13;', '')"/>
    </textarea>
    <xsl:apply-templates select="." mode="common"/>
  </xsl:template>

  <!--+
      | fi:field with @type 'output' and fi:output are both rendered as text
      +-->
  <xsl:template match="fi:output | fi:field[fi:styling/@type='output']" priority="2">
    <xsl:copy-of select="fi:value/node()"/>
  </xsl:template>

  <!--+
      | fi:field with @type 'output' and fi:output are both rendered as text
      +-->
  <xsl:template match="fi:output | fi:field[fi:styling/@type='output']" priority="2">
    <xsl:copy-of select="fi:value/node()"/>
  </xsl:template>

  <!--+
      | fi:booleanfield : produce a checkbox
      +-->
  <xsl:template match="fi:booleanfield">
    <input id="{@id}" type="checkbox" value="true" name="{@id}" title="{fi:hint}">
      <xsl:apply-templates select="." mode="styling"/>
      <xsl:if test="fi:value = 'true'">
        <xsl:attribute name="checked">checked</xsl:attribute>
      </xsl:if>
    </input>
    <xsl:apply-templates select="." mode="common"/>
  </xsl:template>

  <!--+
      | fi:booleanfield with @type 'output' : rendered as text
      +-->
  <xsl:template match="fi:booleanfield[fi:styling/@type='output']">
    <xsl:choose>
      <xsl:when test="fi:value = 'true'">
        yes
      </xsl:when>
      <xsl:otherwise>
        no
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

<!-- addOn -->
  <!--+
      | fi:booleanfield with @type 'index' : rendered as text
      +-->
  <xsl:template match="fi:booleanfield[fi:styling/@type='index']">
    <input id="{@id}" type="checkbox" value="true" name="{@id}" title="{fi:hint}">
      <xsl:apply-templates select="." mode="styling"/>
      <!--
      <xsl:if test="fi:value = @id">
        <xsl:attribute name="checked">checked</xsl:attribute>
      </xsl:if>
      -->
    </input>
    <xsl:apply-templates select="." mode="common"/>
  </xsl:template>

  <xsl:template match="@*|node()" priority="-1">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>

</xsl:stylesheet>
