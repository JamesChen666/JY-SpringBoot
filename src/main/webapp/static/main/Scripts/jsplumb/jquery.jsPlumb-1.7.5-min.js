﻿!
function () {
    "undefined" == typeof Math.sgn && (Math.sgn = function (a) {
        return 0 == a ? 0 : a > 0 ? 1 : -1
    });
    var a = {
        subtract: function (a, b) {
            return {
                x: a.x - b.x,
                y: a.y - b.y
            }
        },
        dotProduct: function (a, b) {
            return a.x * b.x + a.y * b.y
        },
        square: function (a) {
            return Math.sqrt(a.x * a.x + a.y * a.y)
        },
        scale: function (a, b) {
            return {
                x: a.x * b,
                y: a.y * b
            }
        }
    },
    b = 64,
    c = Math.pow(2, -b - 1),
    d = function (b, c) {
        for (var d = [], e = f(b, c), h = c.length - 1, i = 2 * h - 1, j = g(e, i, d, 0), k = a.subtract(b, c[0]), m = a.square(k), n = 0, o = 0; j > o; o++) {
            k = a.subtract(b, l(c, h, d[o], null, null));
            var p = a.square(k);
            m > p && (m = p, n = d[o])
        }
        return k = a.subtract(b, c[h]),
        p = a.square(k),
        m > p && (m = p, n = 1),
        {
            location: n,
            distance: m
        }
    },
    e = function (a, b) {
        var c = d(a, b);
        return {
            point: l(b, b.length - 1, c.location, null, null),
            location: c.location
        }
    },
    f = function (b, c) {
        for (var d = c.length - 1,
        e = 2 * d - 1,
        f = [], g = [], h = [], i = [], k = [[1, .6, .3, .1], [.4, .6, .6, .4], [.1, .3, .6, 1]], l = 0; d >= l; l++) f[l] = a.subtract(c[l], b);
        for (var l = 0; d - 1 >= l; l++) g[l] = a.subtract(c[l + 1], c[l]),
        g[l] = a.scale(g[l], 3);
        for (var m = 0; d - 1 >= m; m++) for (var n = 0; d >= n; n++) h[m] || (h[m] = []),
        h[m][n] = a.dotProduct(g[m], f[n]);
        for (l = 0; e >= l; l++) i[l] || (i[l] = []),
        i[l].y = 0,
        i[l].x = parseFloat(l) / e;
        for (var o = d,
        p = d - 1,
        q = 0; o + p >= q; q++) {
            var r = Math.max(0, q - p),
            s = Math.min(q, o);
            for (l = r; s >= l; l++) j = q - l,
            i[l + j].y += h[j][l] * k[j][l]
        }
        return i
    },
    g = function (a, c, d, e) {
        var f, j, m = [],
        n = [],
        o = [],
        p = [];
        switch (h(a, c)) {
            case 0:
                return 0;
            case 1:
                if (e >= b) return d[0] = (a[0].x + a[c].x) / 2,
                1;
                if (i(a, c)) return d[0] = k(a, c),
                1
        }
        l(a, c, .5, m, n),
        f = g(m, c, o, e + 1),
        j = g(n, c, p, e + 1);
        for (var q = 0; f > q; q++) d[q] = o[q];
        for (var q = 0; j > q; q++) d[q + f] = p[q];
        return f + j
    },
    h = function (a, b) {
        var c, d, e = 0;
        c = d = Math.sgn(a[0].y);
        for (var f = 1; b >= f; f++) c = Math.sgn(a[f].y),
        c != d && e++,
        d = c;
        return e
    },
    i = function (a, b) {
        var d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s;
        i = a[0].y - a[b].y,
        j = a[b].x - a[0].x,
        k = a[0].x * a[b].y - a[b].x * a[0].y;
        for (var t = max_distance_below = 0,
        u = 1; b > u; u++) {
            var v = i * a[u].x + j * a[u].y + k;
            v > t ? t = v : max_distance_below > v && (max_distance_below = v)
        }
        return n = 0,
        o = 1,
        p = 0,
        q = i,
        r = j,
        s = k - t,
        l = n * r - q * o,
        m = 1 / l,
        e = (o * s - r * p) * m,
        q = i,
        r = j,
        s = k - max_distance_below,
        l = n * r - q * o,
        m = 1 / l,
        f = (o * s - r * p) * m,
        g = Math.min(e, f),
        h = Math.max(e, f),
        d = h - g,
        c > d ? 1 : 0
    },
    k = function (a, b) {
        var c = 1,
        d = 0,
        e = a[b].x - a[0].x,
        f = a[b].y - a[0].y,
        g = a[0].x - 0,
        h = a[0].y - 0,
        i = e * d - f * c,
        j = 1 / i,
        k = (e * h - f * g) * j;
        return 0 + c * k
    },
    l = function (a, b, c, d, e) {
        for (var f = [[]], g = 0; b >= g; g++) f[0][g] = a[g];
        for (var h = 1; b >= h; h++) for (var g = 0; b - h >= g; g++) f[h] || (f[h] = []),
        f[h][g] || (f[h][g] = {}),
        f[h][g].x = (1 - c) * f[h - 1][g].x + c * f[h - 1][g + 1].x,
        f[h][g].y = (1 - c) * f[h - 1][g].y + c * f[h - 1][g + 1].y;
        if (null != d) for (g = 0; b >= g; g++) d[g] = f[g][0];
        if (null != e) for (g = 0; b >= g; g++) e[g] = f[b - g][g];
        return f[b][0]
    },
    m = {},
    n = function (a) {
        var b = m[a];
        if (!b) {
            b = [];
            var c = function () {
                return function (b) {
                    return Math.pow(b, a)
                }
            },
            d = function () {
                return function (b) {
                    return Math.pow(1 - b, a)
                }
            },
            e = function (a) {
                return function () {
                    return a
                }
            },
            f = function () {
                return function (a) {
                    return a
                }
            },
            g = function () {
                return function (a) {
                    return 1 - a
                }
            },
            h = function (a) {
                return function (b) {
                    for (var c = 1,
                    d = 0; d < a.length; d++) c *= a[d](b);
                    return c
                }
            };
            b.push(new c);
            for (var i = 1; a > i; i++) {
                for (var j = [new e(a)], k = 0; a - i > k; k++) j.push(new f);
                for (var k = 0; i > k; k++) j.push(new g);
                b.push(new h(j))
            }
            b.push(new d),
            m[a] = b
        }
        return b
    },
    o = function (a, b) {
        for (var c = n(a.length - 1), d = 0, e = 0, f = 0; f < a.length; f++) d += a[f].x * c[f](b),
        e += a[f].y * c[f](b);
        return {
            x: d,
            y: e
        }
    },
    p = function (a, b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2))
    },
    q = function (a) {
        return a[0].x == a[1].x && a[0].y == a[1].y
    },
    r = function (a, b, c) {
        if (q(a)) return {
            point: a[0],
            location: b
        };
        for (var d = o(a, b), e = 0, f = b, g = c > 0 ? 1 : -1, h = null; e < Math.abs(c) ;) f += .005 * g,
        h = o(a, f),
        e += p(h, d),
        d = h;
        return {
            point: h,
            location: f
        }
    },
    s = function (a) {
        if (q(a)) return 0;
        for (var b = o(a, 0), c = 0, d = 0, e = 1, f = null; 1 > d;) d += .005 * e,
        f = o(a, d),
        c += p(f, b),
        b = f;
        return c
    },
    t = function (a, b, c) {
        return r(a, b, c).point
    },
    u = function (a, b, c) {
        return r(a, b, c).location
    },
    v = function (a, b) {
        var c = o(a, b),
        d = o(a.slice(0, a.length - 1), b),
        e = d.y - c.y,
        f = d.x - c.x;
        return 0 == e ? 1 / 0 : Math.atan(e / f)
    },
    w = function (a, b, c) {
        var d = r(a, b, c);
        return d.location > 1 && (d.location = 1),
        d.location < 0 && (d.location = 0),
        v(a, d.location)
    },
    x = function (a, b, c, d) {
        d = null == d ? 0 : d;
        var e = r(a, b, d),
        f = v(a, e.location),
        g = Math.atan(-1 / f),
        h = c / 2 * Math.sin(g),
        i = c / 2 * Math.cos(g);
        return [{
            x: e.point.x + i,
            y: e.point.y + h
        },
        {
            x: e.point.x - i,
            y: e.point.y - h
        }]
    };
    window.jsBezier = {
        distanceFromCurve: d,
        gradientAtPoint: v,
        gradientAtPointAlongCurveFrom: w,
        nearestPointOnCurve: e,
        pointOnCurve: o,
        pointAlongCurveFrom: t,
        perpendicularToCurveAt: x,
        locationAlongCurveFrom: u,
        getLength: s
    }
}(),
function () {
    var a = function (a) {
        return "[object Array]" === Object.prototype.toString.call(a)
    },
    b = function (a) {
        return "[object Number]" === Object.prototype.toString.call(a)
    },
    c = function (a) {
        return "string" == typeof a
    },
    d = function (a) {
        return "boolean" == typeof a
    },
    e = function (a) {
        return null == a
    },
    f = function (a) {
        return null == a ? !1 : "[object Object]" === Object.prototype.toString.call(a)
    },
    g = function (a) {
        return "[object Date]" === Object.prototype.toString.call(a)
    },
    h = function (a) {
        return "[object Function]" === Object.prototype.toString.call(a)
    },
    i = function (a) {
        for (var b in a) if (a.hasOwnProperty(b)) return !1;
        return !0
    },
    j = function (b, c, d) {
        return b = a(b) ? b : [b.x, b.y],
        c = a(c) ? c : [c.x, c.y],
        d(b, c)
    };
    jsPlumbUtil = {
        isArray: a,
        isString: c,
        isBoolean: d,
        isNull: e,
        isObject: f,
        isDate: g,
        isFunction: h,
        isEmpty: i,
        isNumber: b,
        clone: function (b) {
            if (c(b)) return "" + b;
            if (d(b)) return !!b;
            if (g(b)) return new Date(b.getTime());
            if (h(b)) return b;
            if (a(b)) {
                for (var e = [], i = 0; i < b.length; i++) e.push(this.clone(b[i]));
                return e
            }
            if (f(b)) {
                var j = {};
                for (var k in b) j[k] = this.clone(b[k]);
                return j
            }
            return b
        },
        merge: function (b, e) {
            var g = this.clone(b);
            for (var h in e) if (null == g[h] || c(e[h]) || d(e[h])) g[h] = e[h];
            else if (a(e[h])) {
                var i = [];
                a(g[h]) && i.push.apply(i, g[h]),
                i.push.apply(i, e[h]),
                g[h] = i
            } else if (f(e[h])) {
                f(g[h]) || (g[h] = {});
                for (var j in e[h]) g[h][j] = e[h][j]
            }
            return g
        },
        copyValues: function (a, b, c) {
            for (var d = 0; d < a.length; d++) c[a[d]] = b[a[d]]
        },
        functionChain: function (a, b, c) {
            for (var d = 0; d < c.length; d++) {
                var e = c[d][0][c[d][1]].apply(c[d][0], c[d][2]);
                if (e === b) return e
            }
            return a
        },
        populate: function (b, d) {
            var e = function (a) {
                var b = a.match(/(\${.*?})/g);
                if (null != b) for (var c = 0; c < b.length; c++) {
                    var e = d[b[c].substring(2, b[c].length - 1)];
                    null != e && (a = a.replace(b[c], e))
                }
                return a
            },
            g = function (b) {
                if (null != b) {
                    if (c(b)) return e(b);
                    if (a(b)) {
                        for (var d = [], h = 0; h < b.length; h++) d.push(g(b[h]));
                        return d
                    }
                    if (f(b)) {
                        var i = {};
                        for (var j in b) i[j] = g(b[j]);
                        return i
                    }
                    return b
                }
            };
            return g(b)
        },
        convertStyle: function (a, b) {
            if ("transparent" === a) return a;
            var c = a,
            d = function (a) {
                return 1 == a.length ? "0" + a : a
            },
            e = function (a) {
                return d(Number(a).toString(16))
            },
            f = /(rgb[a]?\()(.*)(\))/;
            if (a.match(f)) {
                var g = a.match(f)[2].split(",");
                c = "#" + e(g[0]) + e(g[1]) + e(g[2]),
                b || 4 != g.length || (c += e(g[3]))
            }
            return c
        },
        gradient: function (a, b) {
            return j(a, b,
            function (a, b) {
                return b[0] == a[0] ? b[1] > a[1] ? 1 / 0 : -1 / 0 : b[1] == a[1] ? b[0] > a[0] ? 0 : -0 : (b[1] - a[1]) / (b[0] - a[0])
            })
        },
        normal: function (a, b) {
            return -1 / this.gradient(a, b)
        },
        lineLength: function (a, b) {
            return j(a, b,
            function (a, b) {
                return Math.sqrt(Math.pow(b[1] - a[1], 2) + Math.pow(b[0] - a[0], 2))
            })
        },
        segment: function (a, b) {
            return j(a, b,
            function (a, b) {
                return b[0] > a[0] ? b[1] > a[1] ? 2 : 1 : b[0] == a[0] ? b[1] > a[1] ? 2 : 1 : b[1] > a[1] ? 3 : 4
            })
        },
        theta: function (a, b) {
            return j(a, b,
            function (a, b) {
                var c = jsPlumbUtil.gradient(a, b),
                d = Math.atan(c),
                e = jsPlumbUtil.segment(a, b);
                return (4 == e || 3 == e) && (d += Math.PI),
                0 > d && (d += 2 * Math.PI),
                d
            })
        },
        intersects: function (a, b) {
            var c = a.x,
            d = a.x + a.w,
            e = a.y,
            f = a.y + a.h,
            g = b.x,
            h = b.x + b.w,
            i = b.y,
            j = b.y + b.h;
            return g >= c && d >= g && i >= e && f >= i || h >= c && d >= h && i >= e && f >= i || g >= c && d >= g && j >= e && f >= j || h >= c && d >= g && j >= e && f >= j || c >= g && h >= c && e >= i && j >= e || d >= g && h >= d && e >= i && j >= e || c >= g && h >= c && f >= i && j >= f || d >= g && h >= c && f >= i && j >= f
        },
        segmentMultipliers: [null, [1, -1], [1, 1], [-1, 1], [-1, -1]],
        inverseSegmentMultipliers: [null, [-1, -1], [-1, 1], [1, 1], [1, -1]],
        pointOnLine: function (a, b, c) {
            var d = jsPlumbUtil.gradient(a, b),
            e = jsPlumbUtil.segment(a, b),
            f = c > 0 ? jsPlumbUtil.segmentMultipliers[e] : jsPlumbUtil.inverseSegmentMultipliers[e],
            g = Math.atan(d),
            h = Math.abs(c * Math.sin(g)) * f[1],
            i = Math.abs(c * Math.cos(g)) * f[0];
            return {
                x: a.x + i,
                y: a.y + h
            }
        },
        perpendicularLineTo: function (a, b, c) {
            var d = jsPlumbUtil.gradient(a, b),
            e = Math.atan(-1 / d),
            f = c / 2 * Math.sin(e),
            g = c / 2 * Math.cos(e);
            return [{
                x: b.x + g,
                y: b.y + f
            },
            {
                x: b.x - g,
                y: b.y - f
            }]
        },
        findWithFunction: function (a, b) {
            if (a) for (var c = 0; c < a.length; c++) if (b(a[c])) return c;
            return -1
        },
        clampToGrid: function (a, b, c, d, e) {
            var f = function (a, b) {
                var c = a % b,
                d = Math.floor(a / b),
                e = c >= b / 2 ? 1 : 0;
                return (d + e) * b
            };
            return [d || null == c ? a : f(a, c[0]), e || null == c ? b : f(b, c[1])]
        },
        indexOf: function (a, b) {
            return jsPlumbUtil.findWithFunction(a,
            function (a) {
                return a == b
            })
        },
        removeWithFunction: function (a, b) {
            var c = jsPlumbUtil.findWithFunction(a, b);
            return c > -1 && a.splice(c, 1),
            -1 != c
        },
        remove: function (a, b) {
            var c = jsPlumbUtil.indexOf(a, b);
            return c > -1 && a.splice(c, 1),
            -1 != c
        },
        addWithFunction: function (a, b, c) {
            -1 == jsPlumbUtil.findWithFunction(a, c) && a.push(b)
        },
        addToList: function (a, b, c, d) {
            var e = a[b];
            return null == e && (e = [], a[b] = e),
            e[d ? "unshift" : "push"](c),
            e
        },
        extend: function (b, c, d, e) {
            d = d || {},
            e = e || {},
            c = a(c) ? c : [c];
            for (var f = 0; f < c.length; f++) for (var g in c[f].prototype) c[f].prototype.hasOwnProperty(g) && (b.prototype[g] = c[f].prototype[g]);
            var h = function (a) {
                return function () {
                    for (var b = 0; b < c.length; b++) c[b].prototype[a] && c[b].prototype[a].apply(this, arguments);
                    return d[a].apply(this, arguments)
                }
            };
            for (var i in d) b.prototype[i] = h(i);
            return b
        },
        uuid: function () {
            return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g,
            function (a) {
                var b = 0 | 16 * Math.random(),
                c = "x" == a ? b : 8 | 3 & b;
                return c.toString(16)
            })
        },
        logEnabled: !0,
        log: function () {
            if (jsPlumbUtil.logEnabled && "undefined" != typeof console) try {
                var a = arguments[arguments.length - 1];
                console.log(a)
            } catch (b) { }
        },
        group: function (a) {
            jsPlumbUtil.logEnabled && "undefined" != typeof console && console.group(a)
        },
        groupEnd: function (a) {
            jsPlumbUtil.logEnabled && "undefined" != typeof console && console.groupEnd(a)
        },
        time: function (a) {
            jsPlumbUtil.logEnabled && "undefined" != typeof console && console.time(a)
        },
        timeEnd: function (a) {
            jsPlumbUtil.logEnabled && "undefined" != typeof console && console.timeEnd(a)
        },
        removeElement: function (a) {
            null != a && null != a.parentNode && a.parentNode.removeChild(a)
        },
        removeElements: function (a) {
            for (var b = 0; b < a.length; b++) jsPlumbUtil.removeElement(a[b])
        },
        sizeElement: function (a, b, c, d, e) {
            a && (a.style.height = e + "px", a.height = e, a.style.width = d + "px", a.width = d, a.style.left = b + "px", a.style.top = c + "px")
        },
        wrap: function (a, b, c) {
            return a = a ||
            function () { },
            b = b ||
            function () { },
            function () {
                var d = null;
                try {
                    d = b.apply(this, arguments)
                } catch (e) {
                    jsPlumbUtil.log("jsPlumb function failed : " + e)
                }
                if (null == c || d !== c) try {
                    d = a.apply(this, arguments)
                } catch (e) {
                    jsPlumbUtil.log("wrapped function failed : " + e)
                }
                return d
            }
        }
    },
    jsPlumbUtil.EventGenerator = function () {
        var a = {},
        b = !1,
        c = ["ready"];
        this.bind = function (b, c) {
            return jsPlumbUtil.addToList(a, b, c, !0),
            this
        },
        this.fire = function (d, e, f) {
            if (!b && a[d]) {
                var g = a[d].length,
                h = 0,
                i = !1,
                j = null;
                if (!this.shouldFireEvent || this.shouldFireEvent(d, e, f)) for (; !i && g > h && j !== !1;) {
                    if (-1 != jsPlumbUtil.findWithFunction(c,
                    function (a) {
                        return a === d
                    })) a[d][h](e, f);
                    else try {
                        j = a[d][h](e, f)
                    } catch (k) {
                        jsPlumbUtil.log("jsPlumb: fire failed for event " + d + " : " + k)
                    }
                    h++,
                    (null == a || null == a[d]) && (i = !0)
                }
            }
            return this
        },
        this.unbind = function (b) {
            return b ? delete a[b] : a = {},
            this
        },
        this.getListener = function (b) {
            return a[b]
        },
        this.setSuspendEvents = function (a) {
            b = a
        },
        this.isSuspendEvents = function () {
            return b
        },
        this.cleanupListeners = function () {
            for (var b in a) a[b].splice(0),
            delete a[b]
        }
    },
    jsPlumbUtil.EventGenerator.prototype = {
        cleanup: function () {
            this.cleanupListeners()
        }
    },
    Function.prototype.bind || (Function.prototype.bind = function (a) {
        if ("function" != typeof this) throw new TypeError("Function.prototype.bind - what is trying to be bound is not callable");
        var b = Array.prototype.slice.call(arguments, 1),
        c = this,
        d = function () { },
        e = function () {
            return c.apply(this instanceof d && a ? this : a, b.concat(Array.prototype.slice.call(arguments)))
        };
        return d.prototype = this.prototype,
        e.prototype = new d,
        e
    })
}(),
function () {
    var a = !!document.createElement("canvas").getContext,
    b = !!window.SVGAngle || document.implementation.hasFeature("http://www.w3.org/TR/SVG11/feature#BasicStructure", "1.1"),
    c = function () {
        if (void 0 === c.vml) {
            var a = document.body.appendChild(document.createElement("div"));
            a.innerHTML = '<v:shape id="vml_flag1" adj="1" />';
            var b = a.firstChild;
            null != b && null != b.style ? (b.style.behavior = "url(#default#VML)", c.vml = b ? "object" == typeof b.adj : !0) : c.vml = !1,
            a.parentNode.removeChild(a)
        }
        return c.vml
    },
    d = function (a) {
        var b = {},
        c = [],
        d = {},
        e = {},
        f = {};
        this.register = function (g) {
            var h = jsPlumb.CurrentLibrary,
            i = h.getElementObject(g),
            j = a.getId(g),
            k = h.getOffset(i);
            b[j] || (b[j] = g, c.push(g), d[j] = {});
            var l = function (b) {
                if (b) for (var c = 0; c < b.childNodes.length; c++) if (3 != b.childNodes[c].nodeType && 8 != b.childNodes[c].nodeType) {
                    var g = h.getElementObject(b.childNodes[c]),
                    i = a.getId(b.childNodes[c], null, !0);
                    if (i && e[i] && e[i] > 0) {
                        var m = h.getOffset(g);
                        d[j][i] = {
                            id: i,
                            offset: {
                                left: m.left - k.left,
                                top: m.top - k.top
                            }
                        },
                        f[i] = j
                    }
                    l(b.childNodes[c])
                }
            };
            l(g)
        },
        this.updateOffsets = function (b) {
            var c = jsPlumb.CurrentLibrary,
            e = c.getElementObject(b),
            g = c.getDOMElement(e),
            h = a.getId(g),
            i = d[h],
            j = c.getOffset(e);
            if (i) for (var k in i) {
                var l = c.getElementObject(k),
                m = c.getOffset(l);
                d[h][k] = {
                    id: k,
                    offset: {
                        left: m.left - j.left,
                        top: m.top - j.top
                    }
                },
                f[k] = h
            }
        },
        this.endpointAdded = function (c) {
            var g = jsPlumb.CurrentLibrary,
            h = document.body,
            i = a.getId(c),
            j = g.getElementObject(c),
            k = jsPlumb.CurrentLibrary.getOffset(j),
            l = c.parentNode;
            for (e[i] = e[i] ? e[i] + 1 : 1; null != l && l != h;) {
                var m = a.getId(l, null, !0);
                if (m && b[m]) {
                    var n = g.getElementObject(l),
                    o = g.getOffset(n);
                    null == d[m][i] && (d[m][i] = {
                        id: i,
                        offset: {
                            left: k.left - o.left,
                            top: k.top - o.top
                        }
                    },
                    f[i] = m);
                    break
                }
                l = l.parentNode
            }
        },
        this.endpointDeleted = function (a) {
            if (e[a.elementId] && (e[a.elementId]--, e[a.elementId] <= 0)) for (var b in d) d[b] && (delete d[b][a.elementId], delete f[a.elementId])
        },
        this.changeId = function (a, b) {
            d[b] = d[a],
            d[a] = {},
            f[b] = f[a],
            f[a] = null
        },
        this.getElementsForDraggable = function (a) {
            return d[a]
        },
        this.elementRemoved = function (a) {
            var b = f[a];
            b && (delete d[b][a], delete f[a])
        },
        this.reset = function () {
            b = {},
            c = [],
            d = {},
            e = {}
        }
    };
    window.console || (window.console = {
        time: function () { },
        timeEnd: function () { },
        group: function () { },
        groupEnd: function () { },
        log: function () { }
    }),
    window.jsPlumbAdapter = {
        headless: !1,
        getAttribute: function (a, b) {
            return a.getAttribute(b)
        },
        setAttribute: function (a, b, c) {
            a.setAttribute(b, c)
        },
        appendToRoot: function (a) {
            document.body.appendChild(a)
        },
        getRenderModes: function () {
            return ["canvas", "svg", "vml"]
        },
        isRenderModeAvailable: function (d) {
            return {
                canvas: a,
                svg: b,
                vml: c()
            }[d]
        },
        getDragManager: function (a) {
            return new d(a)
        },
        setRenderMode: function (a) {
            var b;
            if (a) {
                a = a.toLowerCase();
                var c = this.isRenderModeAvailable("canvas"),
                d = this.isRenderModeAvailable("svg"),
                e = this.isRenderModeAvailable("vml");
                "svg" === a ? d ? b = "svg" : c ? b = "canvas" : e && (b = "vml") : "canvas" === a && c ? b = "canvas" : e && (b = "vml")
            }
            return b
        }
    }
}(),
function () {
    var a = jsPlumbUtil,
    b = function (a, b) {
        y.CurrentLibrary.addClass(d(a), b)
    },
    c = function (a, b) {
        y.CurrentLibrary.removeClass(d(a), b)
    },
    d = function (a) {
        return y.CurrentLibrary.getElementObject(a)
    },
    e = function (a) {
        return y.CurrentLibrary.getDOMElement(a)
    },
    f = function (a, b) {
        var c = y.CurrentLibrary.getOffset(d(a));
        if (null != b) {
            var e = b.getZoom();
            return {
                left: c.left / e,
                top: c.top / e
            }
        }
        return c
    },
    g = function (a) {
        return y.CurrentLibrary.getSize(d(a))
    },
    h = function () {
        return "" + (new Date).getTime()
    },
    i = function (a) {
        if (a._jsPlumb.paintStyle && a._jsPlumb.hoverPaintStyle) {
            var b = {};
            y.extend(b, a._jsPlumb.paintStyle),
            y.extend(b, a._jsPlumb.hoverPaintStyle),
            delete a._jsPlumb.hoverPaintStyle,
            b.gradient && a._jsPlumb.paintStyle.fillStyle && delete b.gradient,
            a._jsPlumb.hoverPaintStyle = b
        }
    },
    j = ["click", "dblclick", "mouseenter", "mouseout", "mousemove", "mousedown", "mouseup", "contextmenu"],
    k = {
        mouseout: "mouseexit"
    },
    l = function (a, b, c, d) {
        var e = a.getAttachedElements();
        if (e) for (var f = 0,
        g = e.length; g > f; f++) d && d == e[f] || e[f].setHover(b, !0, c)
    },
    m = function (a) {
        return null == a ? null : a.split(" ")
    },
    n = function (b, c, d) {
        if (b.getDefaultType) {
            for (var e = b.getTypeDescriptor(), f = a.merge({},
            b.getDefaultType()), g = 0, h = b._jsPlumb.types.length; h > g; g++) f = a.merge(f, b._jsPlumb.instance.getType(b._jsPlumb.types[g], e));
            c && (f = a.populate(f, c)),
            b.applyType(f, d),
            d || b.repaint()
        }
    },
    o = window.jsPlumbUIComponent = function (b) {
        jsPlumbUtil.EventGenerator.apply(this, arguments);
        var c = this,
        d = arguments,
        e = c.idPrefix,
        f = e + (new Date).getTime(),
        g = y.CurrentLibrary;
        if (this._jsPlumb = {
            instance: b._jsPlumb,
            parameters: b.parameters || {},
            paintStyle: null,
            hoverPaintStyle: null,
            paintStyleInUse: null,
            hover: !1,
            beforeDetach: b.beforeDetach,
            beforeDrop: b.beforeDrop,
            overlayPlacements: [],
            hoverClass: b.hoverClass || b._jsPlumb.Defaults.HoverClass || y.Defaults.HoverClass,
            types: []
        },
        this.getId = function () {
            return f
        },
        b.events) for (var h in b.events) c.bind(h, b.events[h]);
        this.clone = function () {
            var a = {};
            return this.constructor.apply(a, d),
            a
        }.bind(this),
        this.isDetachAllowed = function (b) {
            var c = !0;
            if (this._jsPlumb.beforeDetach) try {
                c = this._jsPlumb.beforeDetach(b)
            } catch (d) {
                a.log("jsPlumb: beforeDetach callback failed", d)
            }
            return c
        },
        this.isDropAllowed = function (b, c, d, e, f) {
            var g = this._jsPlumb.instance.checkCondition("beforeDrop", {
                sourceId: b,
                targetId: c,
                scope: d,
                connection: e,
                dropEndpoint: f
            });
            if (this._jsPlumb.beforeDrop) try {
                g = this._jsPlumb.beforeDrop({
                    sourceId: b,
                    targetId: c,
                    scope: d,
                    connection: e,
                    dropEndpoint: f
                })
            } catch (h) {
                a.log("jsPlumb: beforeDrop callback failed", h)
            }
            return g
        };
        var i = [],
        l = function (a, b, c) {
            i.push([a, b, c]),
            a.bind(b, c)
        },
        m = [],
        n = function (a, b, c) {
            var d = k[c] || c,
            e = function (a) {
                b.fire(d, b, a)
            };
            m.push([a, c, e]),
            g.bind(a, c, e)
        },
        o = function (a, b, c) {
            k[b] || b,
            g.unbind(a, b, c)
        };
        this.bindListeners = function (a, b, c) {
            l(a, "click",
            function (a, c) {
                b.fire("click", b, c)
            }),
            l(a, "dblclick",
            function (a, c) {
                b.fire("dblclick", b, c)
            }),
            l(a, "contextmenu",
            function (a, c) {
                b.fire("contextmenu", b, c)
            }),
            l(a, "mouseenter",
            function (a, d) {
                b.isHover() || (c(!0), b.fire("mouseenter", b, d))
            }),
            l(a, "mouseexit",
            function (a, d) {
                b.isHover() && (c(!1), b.fire("mouseexit", b, d))
            }),
            l(a, "mousedown",
            function (a, c) {
                b.fire("mousedown", b, c)
            }),
            l(a, "mouseup",
            function (a, c) {
                b.fire("mouseup", b, c)
            })
        },
        this.unbindListeners = function () {
            for (var a = 0; a < i.length; a++) {
                var b = i[a];
                b[0].unbind(b[1], b[2])
            }
            i = null
        },
        this.attachListeners = function (a, b) {
            for (var c = 0,
            d = j.length; d > c; c++) n(a, b, j[c])
        },
        this.detachListeners = function () {
            for (var a = 0; a < m.length; a++) o(m[a][0], m[a][1], m[a][2]);
            m = null
        },
        this.reattachListenersForElement = function (a) {
            if (arguments.length > 1) {
                for (var b = 0,
                c = j.length; c > b; b++) o(a, j[b]);
                for (b = 1, c = arguments.length; c > b; b++) this.attachListeners(a, arguments[b])
            }
        }
    };
    jsPlumbUtil.extend(o, jsPlumbUtil.EventGenerator, {
        getParameter: function (a) {
            return this._jsPlumb.parameters[a]
        },
        setParameter: function (a, b) {
            this._jsPlumb.parameters[a] = b
        },
        getParameters: function () {
            return this._jsPlumb.parameters
        },
        setParameters: function (a) {
            this._jsPlumb.parameters = a
        },
        addClass: function (a) {
            null != this.canvas && b(this.canvas, a)
        },
        removeClass: function (a) {
            null != this.canvas && c(this.canvas, a)
        },
        setType: function (a, b, c) {
            this._jsPlumb.types = m(a) || [],
            n(this, b, c)
        },
        getType: function () {
            return this._jsPlumb.types
        },
        reapplyTypes: function (a, b) {
            n(this, a, b)
        },
        hasType: function (a) {
            return -1 != jsPlumbUtil.indexOf(this._jsPlumb.types, a)
        },
        addType: function (a, b, c) {
            var d = m(a),
            e = !1;
            if (null != d) {
                for (var f = 0,
                g = d.length; g > f; f++) this.hasType(d[f]) || (this._jsPlumb.types.push(d[f]), e = !0);
                e && n(this, b, c)
            }
        },
        removeType: function (b, c) {
            var d = m(b),
            e = !1,
            f = function (b) {
                var c = a.indexOf(this._jsPlumb.types, b);
                return -1 != c ? (this._jsPlumb.types.splice(c, 1), !0) : !1
            }.bind(this);
            if (null != d) {
                for (var g = 0,
                h = d.length; h > g; g++) e = f(d[g]) || e;
                e && n(this, null, c)
            }
        },
        toggleType: function (a, b, c) {
            var d = m(a);
            if (null != d) {
                for (var e = 0,
                f = d.length; f > e; e++) {
                    var g = jsPlumbUtil.indexOf(this._jsPlumb.types, d[e]); -1 != g ? this._jsPlumb.types.splice(g, 1) : this._jsPlumb.types.push(d[e])
                }
                n(this, b, c)
            }
        },
        applyType: function (a, b) {
            if (this.setPaintStyle(a.paintStyle, b), this.setHoverPaintStyle(a.hoverPaintStyle, b), a.parameters) for (var c in a.parameters) this.setParameter(c, a.parameters[c])
        },
        setPaintStyle: function (a, b) {
            this._jsPlumb.paintStyle = a,
            this._jsPlumb.paintStyleInUse = this._jsPlumb.paintStyle,
            i(this),
            b || this.repaint()
        },
        getPaintStyle: function () {
            return this._jsPlumb.paintStyle
        },
        setHoverPaintStyle: function (a, b) {
            this._jsPlumb.hoverPaintStyle = a,
            i(this),
            b || this.repaint()
        },
        getHoverPaintStyle: function () {
            return this._jsPlumb.hoverPaintStyle
        },
        cleanup: function () {
            this.unbindListeners(),
            this.detachListeners()
        },
        destroy: function () {
            this.cleanupListeners(),
            this.clone = null,
            this._jsPlumb = null
        },
        isHover: function () {
            return this._jsPlumb.hover
        },
        setHover: function (a, b, c) {
            var d = y.CurrentLibrary; !this._jsPlumb || this._jsPlumb.instance.currentlyDragging || this._jsPlumb.instance.isHoverSuspended() || (this._jsPlumb.hover = a, null != this.canvas && null != this._jsPlumb.instance.hoverClass && d[a ? "addClass" : "removeClass"](this.canvas, this._jsPlumb.instance.hoverClass), null != this._jsPlumb.hoverPaintStyle && (this._jsPlumb.paintStyleInUse = a ? this._jsPlumb.hoverPaintStyle : this._jsPlumb.paintStyle, this._jsPlumb.instance.isSuspendDrawing() || (c = c || h(), this.repaint({
                timestamp: c,
                recalc: !1
            }))), this.getAttachedElements && !b && l(this, a, h(), this))
        }
    });
    var p = "__label",
    q = function (a, b) {
        for (var c = -1,
        d = 0,
        e = a._jsPlumb.overlays.length; e > d; d++) if (b === a._jsPlumb.overlays[d].id) {
            c = d;
            break
        }
        return c
    },
    r = function (a, b) {
        var c = {
            cssClass: b.cssClass,
            labelStyle: a.labelStyle,
            id: p,
            component: a,
            _jsPlumb: a._jsPlumb.instance
        },
        d = y.extend(c, b);
        return new (y.Overlays[a._jsPlumb.instance.getRenderMode()].Label)(d)
    },
    s = function (b, c) {
        var d = null;
        if (a.isArray(c)) {
            var e = c[0],
            f = y.extend({
                component: b,
                _jsPlumb: b._jsPlumb.instance
            },
            c[1]);
            3 == c.length && y.extend(f, c[2]),
            d = new (y.Overlays[b._jsPlumb.instance.getRenderMode()][e])(f)
        } else d = c.constructor == String ? new (y.Overlays[b._jsPlumb.instance.getRenderMode()][c])({
            component: b,
            _jsPlumb: b._jsPlumb.instance
        }) : c;
        b._jsPlumb.overlays.push(d)
    },
    t = function (a, b) {
        var c = a.defaultOverlayKeys || [],
        d = b.overlays,
        e = function (b) {
            return a._jsPlumb.instance.Defaults[b] || y.Defaults[b] || []
        };
        d || (d = []);
        for (var f = 0,
        g = c.length; g > f; f++) d.unshift.apply(d, e(c[f]));
        return d
    },
    u = window.OverlayCapableJsPlumbUIComponent = function (a) {
        o.apply(this, arguments),
        this._jsPlumb.overlays = [];
        var b = t(this, a);
        if (b) for (var c = 0,
        d = b.length; d > c; c++) s(this, b[c]);
        if (a.label) {
            var e = a.labelLocation || this.defaultLabelLocation || .5,
            f = a.labelStyle || this._jsPlumb.instance.Defaults.LabelStyle || y.Defaults.LabelStyle;
            this._jsPlumb.overlays.push(r(this, {
                label: a.label,
                location: e,
                labelStyle: f
            }))
        }
    };
    jsPlumbUtil.extend(u, o, {
        applyType: function (a, b) {
            if (this.removeAllOverlays(b), a.overlays) for (var c = 0,
            d = a.overlays.length; d > c; c++) this.addOverlay(a.overlays[c], !0)
        },
        setHover: function (a) {
            if (this._jsPlumb && !this._jsPlumb.instance.isConnectionBeingDragged()) for (var b = 0,
            c = this._jsPlumb.overlays.length; c > b; b++) this._jsPlumb.overlays[b][a ? "addClass" : "removeClass"](this._jsPlumb.instance.hoverClass)
        },
        addOverlay: function (a, b) {
            s(this, a),
            b || this.repaint()
        },
        getOverlay: function (a) {
            var b = q(this, a);
            return b >= 0 ? this._jsPlumb.overlays[b] : null
        },
        getOverlays: function () {
            return this._jsPlumb.overlays
        },
        hideOverlay: function (a) {
            var b = this.getOverlay(a);
            b && b.hide()
        },
        hideOverlays: function () {
            for (var a = 0,
            b = this._jsPlumb.overlays.length; b > a; a++) this._jsPlumb.overlays[a].hide()
        },
        showOverlay: function (a) {
            var b = this.getOverlay(a);
            b && b.show()
        },
        showOverlays: function () {
            for (var a = 0,
            b = this._jsPlumb.overlays.length; b > a; a++) this._jsPlumb.overlays[a].show()
        },
        removeAllOverlays: function (a) {
            for (var b = 0,
            c = this._jsPlumb.overlays.length; c > b; b++) this._jsPlumb.overlays[b].cleanup && this._jsPlumb.overlays[b].cleanup();
            this._jsPlumb.overlays.splice(0, this._jsPlumb.overlays.length),
            a || this.repaint()
        },
        removeOverlay: function (a) {
            var b = q(this, a);
            if (-1 != b) {
                var c = this._jsPlumb.overlays[b];
                c.cleanup && c.cleanup(),
                this._jsPlumb.overlays.splice(b, 1)
            }
        },
        removeOverlays: function () {
            for (var a = 0,
            b = arguments.length; b > a; a++) this.removeOverlay(arguments[a])
        },
        getLabel: function () {
            var a = this.getOverlay(p);
            return null != a ? a.getLabel() : null
        },
        getLabelOverlay: function () {
            return this.getOverlay(p)
        },
        setLabel: function (a) {
            var b = this.getOverlay(p);
            if (b) a.constructor == String || a.constructor == Function ? b.setLabel(a) : (a.label && b.setLabel(a.label), a.location && b.setLocation(a.location));
            else {
                var c = a.constructor == String || a.constructor == Function ? {
                    label: a
                } : a;
                b = r(this, c),
                this._jsPlumb.overlays.push(b)
            }
            this._jsPlumb.instance.isSuspendDrawing() || this.repaint()
        },
        cleanup: function () {
            for (var a = 0; a < this._jsPlumb.overlays.length; a++) this._jsPlumb.overlays[a].cleanup(),
            this._jsPlumb.overlays[a].destroy();
            this._jsPlumb.overlays.splice(0)
        }
    });
    var v = 0,
    w = function () {
        var a = v + 1;
        return v++,
        a
    },
    x = window.jsPlumbInstance = function (i) {
        this.Defaults = {
            Anchor: "BottomCenter",
            Anchors: [null, null],
            ConnectionsDetachable: !0,
            ConnectionOverlays: [],
            Connector: "Bezier",
            Container: null,
            DoNotThrowErrors: !1,
            DragOptions: {},
            DropOptions: {},
            Endpoint: "Dot",
            EndpointOverlays: [],
            Endpoints: [null, null],
            EndpointStyle: {
                fillStyle: "#456"
            },
            EndpointStyles: [null, null],
            EndpointHoverStyle: null,
            EndpointHoverStyles: [null, null],
            HoverPaintStyle: null,
            LabelStyle: {
                color: "black"
            },
            LogEnabled: !1,
            Overlays: [],
            MaxConnections: 1,
            PaintStyle: {
                lineWidth: 8,
                strokeStyle: "#456"
            },
            ReattachConnections: !1,
            RenderMode: "svg",
            Scope: "jsPlumb_DefaultScope"
        },
        i && y.extend(this.Defaults, i),
        this.logEnabled = this.Defaults.LogEnabled,
        this._connectionTypes = {},
        this._endpointTypes = {},
        jsPlumbUtil.EventGenerator.apply(this);
        var j = this,
        k = w(),
        l = j.bind,
        m = {},
        n = 1,
        p = function (a) {
            var b = e(a);
            return {
                el: b,
                id: jsPlumbUtil.isString(a) && null == b ? a : bb(b)
            }
        };
        this.getInstanceIndex = function () {
            return k
        },
        this.setZoom = function (a, b) {
            n = a,
            b && j.repaintEverything()
        },
        this.getZoom = function () {
            return n
        };
        for (var q in this.Defaults) m[q] = this.Defaults[q];
        this.bind = function (a, b) {
            "ready" === a && s ? b() : l.apply(j, [a, b])
        },
        j.importDefaults = function (a) {
            for (var b in a) j.Defaults[b] = a[b];
            return j
        },
        j.restoreDefaults = function () {
            return j.Defaults = y.extend({},
            m),
            j
        };
        var r = null,
        s = !1,
        t = [],
        u = {},
        v = {},
        x = {},
        z = {},
        A = {},
        B = {},
        C = !1,
        D = [],
        E = !1,
        F = null,
        G = this.Defaults.Scope,
        H = null,
        I = 1,
        J = function () {
            return "" + I++
        },
        K = function (a, b) {
            j.Defaults.Container ? y.CurrentLibrary.appendElement(a, j.Defaults.Container) : b ? y.CurrentLibrary.appendElement(a, b) : jsPlumbAdapter.appendToRoot(a)
        },
        L = function (a) {
            return a._nodes ? a._nodes : a
        },
        M = function (a, b, c, d) {
            if (!jsPlumbAdapter.headless && !E) {
                var e = bb(a),
                f = j.dragManager.getElementsForDraggable(e);
                null == c && (c = h());
                var g = _({
                    elId: e,
                    offset: b,
                    recalc: !1,
                    timestamp: c
                });
                if (f) for (var i in f) _({
                    elId: f[i].id,
                    offset: {
                        left: g.o.left + f[i].offset.left,
                        top: g.o.top + f[i].offset.top
                    },
                    recalc: !1,
                    timestamp: c
                });
                if (j.anchorManager.redraw(e, b, c, null, d), f) for (var k in f) j.anchorManager.redraw(f[k].id, b, c, f[k].offset, d, !0)
            }
        },
        N = function (b, c) {
            var e, f, g = null;
            if (a.isArray(b)) {
                g = [];
                for (var h = 0,
                i = b.length; i > h; h++) e = d(b[h]),
                f = j.getAttribute(e, "id"),
                g.push(c(e, f))
            } else e = d(b),
            f = j.getAttribute(e, "id"),
            g = c(e, f);
            return g
        },
        O = function (a) {
            return v[a]
        },
        P = function (d, e, f) {
            if (!jsPlumbAdapter.headless) {
                var g = null == e ? !1 : e,
                h = y.CurrentLibrary;
                if (g && h.isDragSupported(d) && !h.isAlreadyDraggable(d)) {
                    var i = f || j.Defaults.DragOptions || y.Defaults.DragOptions;
                    i = y.extend({},
                    i);
                    var k = h.dragEvents.drag,
                    l = h.dragEvents.stop,
                    m = h.dragEvents.start;
                    i[m] = a.wrap(i[m],
                    function () {
                        j.setHoverSuspended(!0),
                        j.select({
                            source: d
                        }).addClass(j.elementDraggingClass + " " + j.sourceElementDraggingClass, !0),
                        j.select({
                            target: d
                        }).addClass(j.elementDraggingClass + " " + j.targetElementDraggingClass, !0),
                        j.setConnectionBeingDragged(!0)
                    }),
                    i[k] = a.wrap(i[k],
                    function () {
                        var a = h.getUIPosition(arguments, j.getZoom());
                        M(d, a, null, !0),
                        b(d, "jsPlumb_dragged")
                    }),
                    i[l] = a.wrap(i[l],
                    function () {
                        var a = h.getUIPosition(arguments, j.getZoom());
                        M(d, a),
                        c(d, "jsPlumb_dragged"),
                        j.setHoverSuspended(!1),
                        j.select({
                            source: d
                        }).removeClass(j.elementDraggingClass + " " + j.sourceElementDraggingClass, !0),
                        j.select({
                            target: d
                        }).removeClass(j.elementDraggingClass + " " + j.targetElementDraggingClass, !0),
                        j.setConnectionBeingDragged(!1)
                    });
                    var n = bb(d);
                    B[n] = !0;
                    var o = B[n];
                    i.disabled = null == o ? !1 : !o,
                    h.initDraggable(d, i, !1, j),
                    j.dragManager.register(d)
                }
            }
        },
        Q = function (b, c) {
            var d = y.extend({},
            b);
            if (c && y.extend(d, c), d.source && (d.source.endpoint ? d.sourceEndpoint = d.source : d.source = e(d.source)), d.target && (d.target.endpoint ? d.targetEndpoint = d.target : d.target = e(d.target)), b.uuids && (d.sourceEndpoint = O(b.uuids[0]), d.targetEndpoint = O(b.uuids[1])), d.sourceEndpoint && d.sourceEndpoint.isFull()) return a.log(j, "could not add connection; source endpoint is full"),
            void 0;
            if (d.targetEndpoint && d.targetEndpoint.isFull()) return a.log(j, "could not add connection; target endpoint is full"),
            void 0;
            if (!d.type && d.sourceEndpoint && (d.type = d.sourceEndpoint.connectionType), d.sourceEndpoint && d.sourceEndpoint.connectorOverlays) {
                d.overlays = d.overlays || [];
                for (var f = 0,
                g = d.sourceEndpoint.connectorOverlays.length; g > f; f++) d.overlays.push(d.sourceEndpoint.connectorOverlays[f])
            } !d["pointer-events"] && d.sourceEndpoint && d.sourceEndpoint.connectorPointerEvents && (d["pointer-events"] = d.sourceEndpoint.connectorPointerEvents);
            var h, i, k, l;
            if (d.target && !d.target.endpoint && !d.targetEndpoint && !d.newConnection && (h = bb(d.target), i = sb[h], k = tb[h], i)) {
                if (!Db[h]) return;
                i.isTarget = !0,
                l = null != k ? k : j.addEndpoint(d.target, i),
                ub[h] && (tb[h] = l),
                d.targetEndpoint = l,
                l._doNotDeleteOnDetach = !1,
                l._deleteOnDetach = !0
            }
            if (d.source && !d.source.endpoint && !d.sourceEndpoint && !d.newConnection && (h = bb(d.source), i = xb[h], k = yb[h], i)) {
                if (!Ab[h]) return;
                l = null != k ? k : j.addEndpoint(d.source, i),
                zb[h] && (yb[h] = l),
                d.sourceEndpoint = l,
                l._doNotDeleteOnDetach = !1,
                l._deleteOnDetach = !0
            }
            return d
        },
        R = function (a) {
            var b = j.Defaults.ConnectionType || j.getDefaultConnectionType(),
            c = j.Defaults.EndpointType || y.Endpoint,
            d = y.CurrentLibrary.getParent;
            a.parent = a.container ? a.container : a.sourceEndpoint ? a.sourceEndpoint.parent : a.source.constructor == c ? a.source.parent : d(a.source),
            a._jsPlumb = j,
            a.newConnection = R,
            a.newEndpoint = V,
            a.endpointsByUUID = v,
            a.endpointsByElement = u,
            a.finaliseConnection = S;
            var e = new b(a);
            return e.id = "con_" + J(),
            T("click", "click", e),
            T("dblclick", "dblclick", e),
            T("contextmenu", "contextmenu", e),
            e
        },
        S = function (a, b, c, d) {
            if (b = b || {},
            a.suspendedEndpoint || t.push(a), (null == a.suspendedEndpoint || d) && j.anchorManager.newConnection(a), M(a.source), !b.doNotFireConnectionEvent && b.fireEvent !== !1) {
                var e = {
                    connection: a,
                    source: a.source,
                    target: a.target,
                    sourceId: a.sourceId,
                    targetId: a.targetId,
                    sourceEndpoint: a.endpoints[0],
                    targetEndpoint: a.endpoints[1]
                };
                j.fire("connection", e, c)
            }
        },
        T = function (a, b, c) {
            c.bind(a,
            function (a, d) {
                j.fire(b, c, d)
            })
        },
        U = function (a) {
            if (a.container) return a.container;
            var b = y.CurrentLibrary.getTagName(a.source),
            c = y.CurrentLibrary.getParent(a.source);
            return b && "td" === b.toLowerCase() ? y.CurrentLibrary.getParent(c) : c
        },
        V = function (a) {
            var b = j.Defaults.EndpointType || y.Endpoint,
            c = y.extend({},
            a);
            c.parent = U(c),
            c._jsPlumb = j,
            c.newConnection = R,
            c.newEndpoint = V,
            c.endpointsByUUID = v,
            c.endpointsByElement = u,
            c.finaliseConnection = S,
            c.fireDetachEvent = cb,
            c.floatingConnections = A,
            c.getParentFromParams = U,
            c.elementId = bb(c.source);
            var d = new b(c);
            return d.id = "ep_" + J(),
            T("click", "endpointClick", d),
            T("dblclick", "endpointDblClick", d),
            T("contextmenu", "contextmenu", d),
            jsPlumbAdapter.headless || j.dragManager.endpointAdded(c.source),
            d
        },
        W = function (a, b, c) {
            var d = u[a];
            if (d && d.length) for (var e = 0,
            f = d.length; f > e; e++) {
                for (var g = 0,
                h = d[e].connections.length; h > g; g++) {
                    var i = b(d[e].connections[g]);
                    if (i) return
                }
                c && c(d[e])
            }
        },
        X = function (a, b) {
            return N(a,
            function (a, c) {
                B[c] = b,
                y.CurrentLibrary.isDragSupported(a) && y.CurrentLibrary.setDraggable(a, b)
            })
        },
        Y = function (a, b, c) {
            b = "block" === b;
            var d = null;
            c && (d = b ?
            function (a) {
                a.setVisible(!0, !0, !0)
            } : function (a) {
                a.setVisible(!1, !0, !0)
            });
            var e = p(a);
            W(e.id,
            function (a) {
                if (b && c) {
                    var d = a.sourceId === e.id ? 1 : 0;
                    a.endpoints[d].isVisible() && a.setVisible(!0)
                } else a.setVisible(b)
            },
            d)
        },
        Z = function (a) {
            return N(a,
            function (a, b) {
                var c = null == B[b] ? !1 : B[b];
                return c = !c,
                B[b] = c,
                y.CurrentLibrary.setDraggable(a, c),
                c
            })
        },
        $ = function (a, b) {
            var c = null;
            b && (c = function (a) {
                var b = a.isVisible();
                a.setVisible(!b)
            }),
            W(a,
            function (a) {
                var b = a.isVisible();
                a.setVisible(!b)
            },
            c)
        },
        _ = function (a) {
            var b, c = a.timestamp,
            e = a.recalc,
            h = a.offset,
            i = a.elId;
            return E && !c && (c = F),
            !e && c && c === z[i] ? {
                o: a.offset || x[i],
                s: D[i]
            } : (e || !h ? (b = d(i), null != b && (D[i] = g(b), x[i] = f(b, j), z[i] = c)) : (x[i] = h, null == D[i] && (b = d(i), null != b && (D[i] = g(b))), z[i] = c), x[i] && !x[i].right && (x[i].right = x[i].left + D[i][0], x[i].bottom = x[i].top + D[i][1], x[i].width = D[i][0], x[i].height = D[i][1], x[i].centerx = x[i].left + x[i].width / 2, x[i].centery = x[i].top + x[i].height / 2), {
                o: x[i],
                s: D[i]
            })
        },
        ab = function (a) {
            var b = x[a];
            return b ? {
                o: b,
                s: D[a]
            } : _({
                elId: a
            })
        },
        bb = function (a, b, c) {
            if (jsPlumbUtil.isString(a)) return a;
            if (null == a) return null;
            var d = jsPlumbAdapter.getAttribute(a, "id");
            return d && "undefined" !== d || (2 == arguments.length && void 0 !== arguments[1] ? d = b : (1 == arguments.length || 3 == arguments.length && !arguments[2]) && (d = "jsPlumb_" + k + "_" + J()), c || jsPlumbAdapter.setAttribute(a, "id", d)),
            d
        };
        this.setConnectionBeingDragged = function (a) {
            C = a
        },
        this.isConnectionBeingDragged = function () {
            return C
        },
        this.connectorClass = "_jsPlumb_connector",
        this.hoverClass = "_jsPlumb_hover",
        this.endpointClass = "_jsPlumb_endpoint",
        this.endpointConnectedClass = "_jsPlumb_endpoint_connected",
        this.endpointFullClass = "_jsPlumb_endpoint_full",
        this.endpointDropAllowedClass = "_jsPlumb_endpoint_drop_allowed",
        this.endpointDropForbiddenClass = "_jsPlumb_endpoint_drop_forbidden",
        this.overlayClass = "_jsPlumb_overlay",
        this.draggingClass = "_jsPlumb_dragging",
        this.elementDraggingClass = "_jsPlumb_element_dragging",
        this.sourceElementDraggingClass = "_jsPlumb_source_element_dragging",
        this.targetElementDraggingClass = "_jsPlumb_target_element_dragging",
        this.endpointAnchorClassPrefix = "_jsPlumb_endpoint_anchor",
        this.hoverSourceClass = "_jsPlumb_source_hover",
        this.hoverTargetClass = "_jsPlumb_target_hover",
        this.dragSelectClass = "_jsPlumb_drag_select",
        this.Anchors = {},
        this.Connectors = {
            canvas: {},
            svg: {},
            vml: {}
        },
        this.Endpoints = {
            canvas: {},
            svg: {},
            vml: {}
        },
        this.Overlays = {
            canvas: {},
            svg: {},
            vml: {}
        },
        this.ConnectorRenderers = {},
        this.SVG = "svg",
        this.CANVAS = "canvas",
        this.VML = "vml",
        this.addEndpoint = function (b, c, d) {
            d = d || {};
            var f = y.extend({},
            d);
            y.extend(f, c),
            f.endpoint = f.endpoint || j.Defaults.Endpoint || y.Defaults.Endpoint,
            f.paintStyle = f.paintStyle || j.Defaults.EndpointStyle || y.Defaults.EndpointStyle,
            b = L(b);
            for (var g = [], h = a.isArray(b) || null != b.length && !a.isString(b) ? b : [b], i = 0, k = h.length; k > i; i++) {
                var l = e(h[i]),
                m = bb(l);
                f.source = l,
                _({
                    elId: m,
                    timestamp: F
                });
                var n = V(f);
                f.parentAnchor && (n.parentAnchor = f.parentAnchor),
                a.addToList(u, m, n);
                var o = x[m],
                p = D[m],
                q = n.anchor.compute({
                    xy: [o.left, o.top],
                    wh: p,
                    element: n,
                    timestamp: F
                }),
                r = {
                    anchorLoc: q,
                    timestamp: F
                };
                E && (r.recalc = !1),
                E || n.paint(r),
                g.push(n),
                n._doNotDeleteOnDetach = !0
            }
            return 1 == g.length ? g[0] : g
        },
        this.addEndpoints = function (b, c, d) {
            for (var e = [], f = 0, g = c.length; g > f; f++) {
                var h = j.addEndpoint(b, c[f], d);
                a.isArray(h) ? Array.prototype.push.apply(e, h) : e.push(h)
            }
            return e
        },
        this.animate = function (b, c, e) {
            e = e || {};
            var f = d(b),
            g = bb(b),
            h = y.CurrentLibrary.dragEvents.step,
            i = y.CurrentLibrary.dragEvents.complete;
            e[h] = a.wrap(e[h],
            function () {
                j.repaint(g)
            }),
            e[i] = a.wrap(e[i],
            function () {
                j.repaint(g)
            }),
            y.CurrentLibrary.animate(f, c, e)
        },
        this.checkCondition = function (b, c) {
            var d = j.getListener(b),
            e = !0;
            if (d && d.length > 0) try {
                for (var f = 0,
                g = d.length; g > f; f++) e = e && d[f](c)
            } catch (h) {
                a.log(j, "cannot check condition [" + b + "]" + h)
            }
            return e
        },
        this.checkASyncCondition = function (b, c, d, e) {
            var f = j.getListener(b);
            if (f && f.length > 0) try {
                f[0](c, d, e)
            } catch (g) {
                a.log(j, "cannot asynchronously check condition [" + b + "]" + g)
            }
        },
        this.connect = function (a, b) {
            var c, d = Q(a, b);
            return d && (c = R(d), S(c, d)),
            c
        },
        this.deleteEndpoint = function (a, b) {
            var c = j.setSuspendDrawing(!0),
            d = "string" == typeof a ? v[a] : a;
            return d && j.deleteObject({
                endpoint: d
            }),
            c || j.setSuspendDrawing(!1, b),
            j
        },
        this.deleteEveryEndpoint = function () {
            var a = j.setSuspendDrawing(!0);
            for (var b in u) {
                var c = u[b];
                if (c && c.length) for (var d = 0,
                e = c.length; e > d; d++) j.deleteEndpoint(c[d], !0)
            }
            return u = {},
            v = {},
            j.anchorManager.reset(),
            j.dragManager.reset(),
            a || j.setSuspendDrawing(!1),
            j
        };
        var cb = function (a, b, c) {
            var d = j.Defaults.ConnectionType || j.getDefaultConnectionType(),
            e = a.constructor == d,
            f = e ? {
                connection: a,
                source: a.source,
                target: a.target,
                sourceId: a.sourceId,
                targetId: a.targetId,
                sourceEndpoint: a.endpoints[0],
                targetEndpoint: a.endpoints[1]
            } : a;
            b && j.fire("connectionDetached", f, c),
            j.anchorManager.connectionDetached(f)
        };
        this.unregisterEndpoint = function (a) {
            a._jsPlumb.uuid && (v[a._jsPlumb.uuid] = null),
            j.anchorManager.deleteEndpoint(a);
            for (var b in u) {
                var c = u[b];
                if (c) {
                    for (var d = [], e = 0, f = c.length; f > e; e++) c[e] != a && d.push(c[e]);
                    u[b] = d
                }
                u[b].length < 1 && delete u[b]
            }
        },
        this.detach = function () {
            if (0 !== arguments.length) {
                var a = j.Defaults.ConnectionType || j.getDefaultConnectionType(),
                b = arguments[0].constructor == a,
                c = 2 == arguments.length ? b ? arguments[1] || {} : arguments[0] : arguments[0],
                d = c.fireEvent !== !1,
                f = c.forceDetach,
                g = b ? arguments[0] : c.connection;
                if (g) (f || jsPlumbUtil.functionChain(!0, !1, [[g.endpoints[0], "isDetachAllowed", [g]], [g.endpoints[1], "isDetachAllowed", [g]], [g, "isDetachAllowed", [g]], [j, "checkCondition", ["beforeDetach", g]]])) && g.endpoints[0].detach(g, !1, !0, d);
                else {
                    var h = y.extend({},
                    c);
                    if (h.uuids) O(h.uuids[0]).detachFrom(O(h.uuids[1]), d);
                    else if (h.sourceEndpoint && h.targetEndpoint) h.sourceEndpoint.detachFrom(h.targetEndpoint);
                    else {
                        var i = bb(e(h.source)),
                        k = bb(e(h.target));
                        W(i,
                        function (a) {
                            (a.sourceId == i && a.targetId == k || a.targetId == i && a.sourceId == k) && j.checkCondition("beforeDetach", a) && a.endpoints[0].detach(a, !1, !0, d)
                        })
                    }
                }
            }
        },
        this.detachAllConnections = function (a, b) {
            b = b || {},
            a = e(a);
            var c = bb(a),
            d = u[c];
            if (d && d.length) for (var f = 0,
            g = d.length; g > f; f++) d[f].detachAll(b.fireEvent !== !1);
            return j
        },
        this.detachEveryConnection = function (a) {
            return a = a || {},
            j.doWhileSuspended(function () {
                for (var b in u) {
                    var c = u[b];
                    if (c && c.length) for (var d = 0,
                    e = c.length; e > d; d++) c[d].detachAll(a.fireEvent !== !1)
                }
                t.splice(0)
            }),
            j
        },
        this.deleteObject = function (a) {
            var b = {
                endpoints: {},
                connections: {},
                endpointCount: 0,
                connectionCount: 0
            },
            c = a.fireEvent !== !1,
            d = a.deleteAttachedObjects !== !1,
            e = function (a) {
                if (null != a && null == b.connections[a.id] && (a._jsPlumb && a.setHover(!1), b.connections[a.id] = a, b.connectionCount++, d)) for (var c = 0; c < a.endpoints.length; c++) a.endpoints[c]._deleteOnDetach && f(a.endpoints[c])
            },
            f = function (a) {
                if (null != a && null == b.endpoints[a.id] && (a._jsPlumb && a.setHover(!1), b.endpoints[a.id] = a, b.endpointCount++, d)) for (var c = 0; c < a.connections.length; c++) {
                    var f = a.connections[c];
                    e(f)
                }
            };
            a.connection ? e(a.connection) : f(a.endpoint);
            for (var g in b.connections) {
                var h = b.connections[g];
                h.endpoints[0].detachFromConnection(h),
                h.endpoints[1].detachFromConnection(h),
                jsPlumbUtil.removeWithFunction(t,
                function (a) {
                    return h.id == a.id
                }),
                cb(h, c, a.originalEvent),
                h.cleanup(),
                h.destroy()
            }
            for (var i in b.endpoints) {
                var k = b.endpoints[i];
                j.unregisterEndpoint(k),
                k.cleanup(),
                k.destroy()
            }
            return b
        },
        this.draggable = function (a, b) {
            var c, d, f;
            if ("object" == typeof a && a.length) for (c = 0, d = a.length; d > c; c++) f = e(a[c]),
            f && P(f, !0, b);
            else if (a._nodes) for (c = 0, d = a._nodes.length; d > c; c++) f = e(a._nodes[c]),
            f && P(f, !0, b);
            else f = e(a),
            f && P(f, !0, b);
            return j
        },
        this.extend = function (a, b) {
            return y.CurrentLibrary.extend(a, b)
        };
        var db = function (a, b, c, d) {
            for (var e = 0,
            f = a.length; f > e; e++) a[e][b].apply(a[e], c);
            return d(a)
        },
        eb = function (a, b, c) {
            for (var d = [], e = 0, f = a.length; f > e; e++) d.push([a[e][b].apply(a[e], c), a[e]]);
            return d
        },
        fb = function (a, b, c) {
            return function () {
                return db(a, b, arguments, c)
            }
        },
        gb = function (a, b) {
            return function () {
                return eb(a, b, arguments)
            }
        },
        hb = function (a, b) {
            var c = [];
            if (a) if ("string" == typeof a) {
                if ("*" === a) return a;
                c.push(a)
            } else if (a = d(a), b) c = a;
            else for (var e = 0,
            f = a.length; f > e; e++) c.push(p(a[e]).id);
            return c
        },
        ib = function (a, b, c) {
            return "*" === a ? !0 : a.length > 0 ? -1 != jsPlumbUtil.indexOf(a, b) : !c
        };
        this.getConnections = function (a, b) {
            a ? a.constructor == String && (a = {
                scope: a
            }) : a = {};
            for (var c = a.scope || j.getDefaultScope(), d = hb(c, !0), e = hb(a.source), f = hb(a.target), g = !b && d.length > 1 ? {} : [], h = function (a, c) {
                if (!b && d.length > 1) {
                    var e = g[a];
                    null == e && (e = g[a] = []),
                    e.push(c)
            } else g.push(c)
            },
            i = 0, k = t.length; k > i; i++) {
                var l = t[i];
                ib(d, l.scope) && ib(e, l.sourceId) && ib(f, l.targetId) && h(l.scope, l)
            }
            return g
        };
        var jb = function (a, b) {
            return function (c) {
                for (var d = 0,
                e = a.length; e > d; d++) c(a[d]);
                return b(a)
            }
        },
        kb = function (a) {
            return function (b) {
                return a[b]
            }
        },
        lb = function (a, b) {
            var c, d, e = {
                length: a.length,
                each: jb(a, b),
                get: kb(a)
            },
            f = ["setHover", "removeAllOverlays", "setLabel", "addClass", "addOverlay", "removeOverlay", "removeOverlays", "showOverlay", "hideOverlay", "showOverlays", "hideOverlays", "setPaintStyle", "setHoverPaintStyle", "setSuspendEvents", "setParameter", "setParameters", "setVisible", "repaint", "addType", "toggleType", "removeType", "removeClass", "setType", "bind", "unbind"],
            g = ["getLabel", "getOverlay", "isHover", "getParameter", "getParameters", "getPaintStyle", "getHoverPaintStyle", "isVisible", "hasType", "getType", "isSuspendEvents"];
            for (c = 0, d = f.length; d > c; c++) e[f[c]] = fb(a, f[c], b);
            for (c = 0, d = g.length; d > c; c++) e[g[c]] = gb(a, g[c]);
            return e
        },
        mb = function (a) {
            var b = lb(a, mb);
            return y.CurrentLibrary.extend(b, {
                setDetachable: fb(a, "setDetachable", mb),
                setReattach: fb(a, "setReattach", mb),
                setConnector: fb(a, "setConnector", mb),
                detach: function () {
                    for (var b = 0,
                    c = a.length; c > b; b++) j.detach(a[b])
                },
                isDetachable: gb(a, "isDetachable"),
                isReattach: gb(a, "isReattach")
            })
        },
        nb = function (a) {
            var b = lb(a, nb);
            return y.CurrentLibrary.extend(b, {
                setEnabled: fb(a, "setEnabled", nb),
                setAnchor: fb(a, "setAnchor", nb),
                isEnabled: gb(a, "isEnabled"),
                detachAll: function () {
                    for (var b = 0,
                    c = a.length; c > b; b++) a[b].detachAll()
                },
                remove: function () {
                    for (var b = 0,
                    c = a.length; c > b; b++) j.deleteObject({
                        endpoint: a[b]
                    })
                }
            })
        };
        this.select = function (a) {
            return a = a || {},
            a.scope = a.scope || "*",
            mb(a.connections || j.getConnections(a, !0))
        },
        this.selectEndpoints = function (a) {
            a = a || {},
            a.scope = a.scope || "*";
            var b = !a.element && !a.source && !a.target,
            c = b ? "*" : hb(a.element),
            d = b ? "*" : hb(a.source),
            e = b ? "*" : hb(a.target),
            f = hb(a.scope, !0),
            g = [];
            for (var h in u) {
                var i = ib(c, h, !0),
                j = ib(d, h, !0),
                k = "*" != d,
                l = ib(e, h, !0),
                m = "*" != e;
                if (i || j || l) a: for (var n = 0,
                o = u[h].length; o > n; n++) {
                    var p = u[h][n];
                    if (ib(f, p.scope, !0)) {
                        var q = k && d.length > 0 && !p.isSource,
                        r = m && e.length > 0 && !p.isTarget;
                        if (q || r) continue a;
                        g.push(p)
                    }
                }
            }
            return nb(g)
        },
        this.getAllConnections = function () {
            return t
        },
        this.getDefaultScope = function () {
            return G
        },
        this.getEndpoint = O,
        this.getEndpoints = function (a) {
            return u[p(a).id]
        },
        this.getDefaultEndpointType = function () {
            return y.Endpoint
        },
        this.getDefaultConnectionType = function () {
            return y.Connection
        },
        this.getId = bb,
        this.getOffset = function (a) {
            return x[a],
            _({
                elId: a
            })
        },
        this.getSelector = function () {
            return y.CurrentLibrary.getSelector.apply(null, arguments)
        },
        this.getSize = function (a) {
            var b = D[a];
            return b || _({
                elId: a
            }),
            D[a]
        },
        this.appendElement = K;
        var ob = !1;
        this.isHoverSuspended = function () {
            return ob
        },
        this.setHoverSuspended = function (a) {
            ob = a
        };
        var pb = function (a) {
            return function () {
                return jsPlumbAdapter.isRenderModeAvailable(a)
            }
        };
        this.isCanvasAvailable = pb("canvas"),
        this.isSVGAvailable = pb("svg"),
        this.isVMLAvailable = pb("vml"),
        this.hide = function (a, b) {
            return Y(a, "none", b),
            j
        },
        this.idstamp = J,
        this.connectorsInitialized = !1;
        var qb = [],
        rb = ["canvas", "svg", "vml"];
        this.registerConnectorType = function (a, b) {
            qb.push([a, b])
        },
        this.init = function () {
            var a = function (a, b, c) {
                y.Connectors[a][b] = function () {
                    c.apply(this, arguments),
                    y.ConnectorRenderers[a].apply(this, arguments)
                },
                jsPlumbUtil.extend(y.Connectors[a][b], [c, y.ConnectorRenderers[a]])
            };
            if (!y.connectorsInitialized) {
                for (var b = 0; b < qb.length; b++) for (var c = 0; c < rb.length; c++) a(rb[c], qb[b][1], qb[b][0]);
                y.connectorsInitialized = !0
            }
            s || (j.anchorManager = new y.AnchorManager({
                jsPlumbInstance: j
            }), j.setRenderMode(j.Defaults.RenderMode), s = !0, j.fire("ready", j))
        }.bind(this),
        this.log = r,
        this.jsPlumbUIComponent = o,
        this.makeAnchor = function () {
            var b = function (a, b) {
                if (y.Anchors[a]) return new y.Anchors[a](b);
                if (!j.Defaults.DoNotThrowErrors) throw {
                    msg: "jsPlumb: unknown anchor type '" + a + "'"
                }
            };
            if (0 === arguments.length) return null;
            var c = arguments[0],
            d = arguments[1],
            e = arguments[2],
            f = null;
            if (c.compute && c.getOrientation) return c;
            if ("string" == typeof c) f = b(arguments[0], {
                elementId: d,
                jsPlumbInstance: j
            });
            else if (a.isArray(c)) if (a.isArray(c[0]) || a.isString(c[0])) if (2 == c.length && a.isString(c[0]) && a.isObject(c[1])) {
                var g = y.extend({
                    elementId: d,
                    jsPlumbInstance: j
                },
                c[1]);
                f = b(c[0], g)
            } else f = new y.DynamicAnchor({
                anchors: c,
                selector: null,
                elementId: d,
                jsPlumbInstance: e
            });
            else {
                var h = {
                    x: c[0],
                    y: c[1],
                    orientation: c.length >= 4 ? [c[2], c[3]] : [0, 0],
                    offsets: c.length >= 6 ? [c[4], c[5]] : [0, 0],
                    elementId: d,
                    jsPlumbInstance: e,
                    cssClass: 7 == c.length ? c[6] : null
                };
                f = new y.Anchor(h),
                f.clone = function () {
                    return new y.Anchor(h)
                }
            }
            return f.id || (f.id = "anchor_" + J()),
            f
        },
        this.makeAnchors = function (b, c, d) {
            for (var e = [], f = 0, g = b.length; g > f; f++) "string" == typeof b[f] ? e.push(y.Anchors[b[f]]({
                elementId: c,
                jsPlumbInstance: d
            })) : a.isArray(b[f]) && e.push(j.makeAnchor(b[f], c, d));
            return e
        },
        this.makeDynamicAnchor = function (a, b) {
            return new y.DynamicAnchor({
                anchors: a,
                selector: b,
                elementId: null,
                jsPlumbInstance: j
            })
        };
        var sb = {},
        tb = {},
        ub = {},
        vb = {},
        wb = function (a, b) {
            a.paintStyle = a.paintStyle || j.Defaults.EndpointStyles[b] || j.Defaults.EndpointStyle || y.Defaults.EndpointStyles[b] || y.Defaults.EndpointStyle,
            a.hoverPaintStyle = a.hoverPaintStyle || j.Defaults.EndpointHoverStyles[b] || j.Defaults.EndpointHoverStyle || y.Defaults.EndpointHoverStyles[b] || y.Defaults.EndpointHoverStyle,
            a.anchor = a.anchor || j.Defaults.Anchors[b] || j.Defaults.Anchor || y.Defaults.Anchors[b] || y.Defaults.Anchor,
            a.endpoint = a.endpoint || j.Defaults.Endpoints[b] || j.Defaults.Endpoint || y.Defaults.Endpoints[b] || y.Defaults.Endpoint
        },
        xb = {},
        yb = {},
        zb = {},
        Ab = {},
        Bb = {},
        Cb = {},
        Db = {},
        Eb = function (a, b, c) {
            for (var d = a.target || a.srcElement,
            e = !1,
            f = j.getSelector(b, c), g = 0; g < f.length; g++) if (f[g] == d) {
                e = !0;
                break
            }
            return e
        };
        this.makeTarget = function (b, c, e) {
            var h = y.extend({
                _jsPlumb: j
            },
            e);
            y.extend(h, c),
            wb(h, 1);
            var i = y.CurrentLibrary,
            k = h.scope || j.Defaults.Scope,
            l = !(h.deleteEndpointsOnDetach === !1),
            m = h.maxConnections || -1,
            n = h.onMaxConnections;
            _doOne = function (b) {
                var c = p(b),
                e = c.id,
                q = new o(h),
                r = y.extend({},
                h.dropOptions || {});
                sb[e] = h,
                ub[e] = h.uniqueEndpoint,
                vb[e] = m,
                Db[e] = !0;
                var s = function () {
                    j.currentlyDragging = !1;
                    var a = y.CurrentLibrary.getDropEvent(arguments),
                    b = j.select({
                        target: e
                    }).length,
                    k = d(i.getDragObject(arguments)),
                    m = j.getAttribute(k, "dragId"),
                    o = j.getAttribute(k, "originalScope"),
                    p = A[m],
                    r = p.endpoints[0].isFloating() ? 0 : 1,
                    s = p.endpoints[0];
                    if (h.endpoint ? y.extend({},
                    h.endpoint) : {},
                    !Db[e] || vb[e] > 0 && b >= vb[e]) return n && n({
                        element: c.el,
                        connection: p
                    },
                    a),
                    !1;
                    s.anchor.locked = !1,
                    o && i.setDragScope(k, o);
                    var t = q.isDropAllowed(0 === r ? e : p.sourceId, 0 === r ? p.targetId : e, p.scope, p, null);
                    if (p.suspendedEndpoint && (p[r ? "targetId" : "sourceId"] = p.suspendedEndpoint.elementId, p[r ? "target" : "source"] = p.suspendedEndpoint.element, p.endpoints[r] = p.suspendedEndpoint), t) {
                        var u = i.getElementObject(c.el),
                        v = tb[e] || j.addEndpoint(u, h);
                        if (h.uniqueEndpoint && (tb[e] = v), v._doNotDeleteOnDetach = !1, v._deleteOnDetach = !0, null != v.anchor.positionFinder) {
                            var w = i.getUIPosition(arguments, j.getZoom()),
                            x = f(u, j),
                            z = g(u),
                            B = v.anchor.positionFinder(w, x, z, v.anchor.constructorParams);
                            v.anchor.x = B[0],
                            v.anchor.y = B[1]
                        }
                        p[r ? "target" : "source"] = v.element,
                        p[r ? "targetId" : "sourceId"] = v.elementId,
                        p.endpoints[r].detachFromConnection(p),
                        p.endpoints[r]._deleteOnDetach && (p.endpoints[r].deleteAfterDragStop = !0),
                        v.addConnection(p),
                        p.endpoints[r] = v,
                        p.deleteEndpointsOnDetach = l,
                        1 == r ? j.anchorManager.updateOtherEndpoint(p.sourceId, p.suspendedElementId, p.targetId, p) : j.anchorManager.sourceChanged(p.suspendedEndpoint.elementId, p.sourceId, p),
                        S(p, null, a)
                    } else p.suspendedEndpoint && (p.isReattach() ? (p.setHover(!1), p.floatingAnchorIndex = null, p.suspendedEndpoint.addConnection(p), j.repaint(s.elementId)) : s.detach(p, !1, !0, !0, a))
                },
                t = i.dragEvents.drop;
                r.scope = r.scope || k,
                r[t] = a.wrap(r[t], s),
                i.initDroppable(d(c.el), r, !0)
            },
            b = L(b);
            for (var q = b.length && b.constructor != String ? b : [b], r = 0, s = q.length; s > r; r++) _doOne(q[r]);
            return j
        },
        this.unmakeTarget = function (a, b) {
            var c = p(a);
            return y.CurrentLibrary.destroyDroppable(c.el),
            b || (delete sb[c.id], delete ub[c.id], delete vb[c.id], delete Db[c.id]),
            j
        },
        this.makeSource = function (b, c, f) {
            var g = y.extend({},
            f);
            y.extend(g, c),
            wb(g, 0);
            var h = y.CurrentLibrary,
            i = g.maxConnections || -1,
            k = g.onMaxConnections,
            l = function (b) {
                var c = b.id,
                f = d(b.el),
                l = function () {
                    return null == g.parent ? null : "parent" === g.parent ? b.el.parentNode : e(g.parent)
                },
                m = null != g.parent ? j.getId(l()) : c;
                xb[m] = g,
                zb[m] = g.uniqueEndpoint,
                Ab[m] = !0;
                var n = h.dragEvents.stop,
                o = h.dragEvents.drag,
                p = y.extend({},
                g.dragOptions || {}),
                q = p.drag,
                r = p.stop,
                s = null,
                u = !1;
                Cb[m] = i,
                p.scope = p.scope || g.scope,
                p[o] = a.wrap(p[o],
                function () {
                    q && q.apply(this, arguments),
                    u = !1
                }),
                p[n] = a.wrap(p[n],
                function () {
                    if (r && r.apply(this, arguments), j.currentlyDragging = !1, null != s._jsPlumb) {
                        h.unbind(s.canvas, "mousedown");
                        var a = g.anchor || j.Defaults.Anchor,
                        b = (s.anchor, s.connections[0]);
                        if (s.setAnchor(j.makeAnchor(a, c, j), !0), g.parent) {
                            var d = l();
                            if (d) {
                                var e = (s.elementId, g.container || j.Defaults.Container || y.Defaults.Container);
                                s.setElement(d, e),
                                s.endpointWillMoveAfterConnection = !1,
                                b.previousConnection = null,
                                jsPlumbUtil.removeWithFunction(t,
                                function (a) {
                                    return a.id === b.id
                                }),
                                j.anchorManager.connectionDetached({
                                    sourceId: b.sourceId,
                                    targetId: b.targetId,
                                    connection: b
                                }),
                                S(b)
                            }
                        }
                        s.repaint(),
                        j.repaint(s.elementId),
                        j.repaint(b.targetId)
                    }
                });
                var v = function (a) {
                    if (Ab[m]) {
                        if (g.filter) {
                            var b = h.getOriginalEvent(a),
                            d = jsPlumbUtil.isString(g.filter) ? Eb(b, f, g.filter) : g.filter(b, f);
                            if (d === !1) return
                        }
                        var e = j.select({
                            source: m
                        }).length;
                        if (Cb[m] >= 0 && e >= Cb[m]) return k && k({
                            element: f,
                            maxConnections: i
                        },
                        a),
                        !1;
                        var n = _({
                            elId: c
                        }).o,
                        o = j.getZoom(),
                        q = ((a.pageX || a.page.x) / o - n.left) / n.width,
                        r = ((a.pageY || a.page.y) / o - n.top) / n.height,
                        t = q,
                        v = r;
                        if (g.parent) {
                            var w = l(),
                            x = bb(w);
                            n = _({
                                elId: x
                            }).o,
                            t = ((a.pageX || a.page.x) - n.left) / n.width,
                            v = ((a.pageY || a.page.y) - n.top) / n.height
                        }
                        var z = {};
                        if (y.extend(z, g), z.isSource = !0, z.anchor = [q, r, 0, 0], z.parentAnchor = [t, v, 0, 0], z.dragOptions = p, g.parent) {
                            var A = z.container || j.Defaults.Container || y.Defaults.Container;
                            z.container = A ? A : y.CurrentLibrary.getParent(l())
                        }
                        s = j.addEndpoint(c, z),
                        u = !0,
                        s.endpointWillMoveAfterConnection = null != g.parent,
                        s.endpointWillMoveTo = g.parent ? l() : null,
                        s._doNotDeleteOnDetach = !1,
                        s._deleteOnDetach = !0;
                        var B = function () {
                            u && (u = !1, j.deleteEndpoint(s))
                        };
                        j.registerListener(s.canvas, "mouseup", B),
                        j.registerListener(f, "mouseup", B),
                        h.trigger(s.canvas, "mousedown", a)
                    }
                };
                j.registerListener(f, "mousedown", v),
                Bb[c] = v,
                g.filter && jsPlumbUtil.isString(g.filter) && h.setDragFilter(f, g.filter)
            };
            b = L(b);
            for (var m = b.length && b.constructor != String ? b : [b], n = 0, o = m.length; o > n; n++) l(p(m[n]));
            return j
        },
        this.unmakeSource = function (a, b) {
            var c = p(a),
            d = Bb[c.id];
            return d && j.unregisterListener(c.el, "mousedown", d),
            b || (delete xb[c.id], delete zb[c.id], delete Ab[c.id], delete Bb[c.id], delete Cb[c.id]),
            j
        },
        this.unmakeEverySource = function () {
            for (var a in Ab) j.unmakeSource(a, !0);
            xb = {},
            zb = {},
            Ab = {},
            Bb = {}
        },
        this.unmakeEveryTarget = function () {
            for (var a in Db) j.unmakeTarget(a, !0);
            return sb = {},
            ub = {},
            vb = {},
            Db = {},
            j
        };
        var Fb = function (b, c, d, e) {
            var f = "source" == b ? Ab : Db;
            if (c = L(c), a.isString(c)) f[c] = e ? !f[c] : d;
            else if (c.length) for (var g = 0,
            h = c.length; h > g; g++) {
                var i = p(c[g]);
                f[i.id] = e ? !f[i.id] : d
            }
            return j
        };
        this.toggleSourceEnabled = function (a) {
            return Fb("source", a, null, !0),
            j.isSourceEnabled(a)
        },
        this.setSourceEnabled = function (a, b) {
            return Fb("source", a, b)
        },
        this.isSource = function (a) {
            return null != Ab[p(a).id]
        },
        this.isSourceEnabled = function (a) {
            return Ab[p(a).id] === !0
        },
        this.toggleTargetEnabled = function (a) {
            return Fb("target", a, null, !0),
            j.isTargetEnabled(a)
        },
        this.isTarget = function (a) {
            return null != Db[p(a).id]
        },
        this.isTargetEnabled = function (a) {
            return Db[p(a).id] === !0
        },
        this.setTargetEnabled = function (a, b) {
            return Fb("target", a, b)
        },
        this.ready = function (a) {
            j.bind("ready", a)
        },
        this.repaint = function (a, b, c) {
            if ("object" == typeof a && a.length) for (var d = 0,
            e = a.length; e > d; d++) M(a[d], b, c);
            else M(a, b, c);
            return j
        },
        this.repaintEverything = function () {
            var a = h();
            for (var b in u) M(b, null, a);
            return j
        },
        this.removeAllEndpoints = function (a, b) {
            var c = function (a) {
                var d, e, f = p(a),
                g = u[f.id];
                if (g) for (d = 0, e = g.length; e > d; d++) j.deleteEndpoint(g[d]);
                if (delete u[f.id], b && f.el && 3 != f.el.nodeType && 8 != f.el.nodeType) for (d = 0, e = f.el.childNodes.length; e > d; d++) c(f.el.childNodes[d])
            };
            return c(a),
            j
        },
        this.remove = function (a, b) {
            var c = p(a);
            j.doWhileSuspended(function () {
                j.removeAllEndpoints(c.id, !0),
                j.dragManager.elementRemoved(c.id),
                delete A[c.id],
                j.anchorManager.clearFor(c.id),
                j.anchorManager.removeFloatingConnection(c.id)
            },
            b === !1),
            c.el && y.CurrentLibrary.removeElement(c.el)
        };
        var Gb = {},
        Hb = function () {
            for (var a in Gb) for (var b = 0,
            c = Gb[a].length; c > b; b++) {
                var d = Gb[a][b];
                y.CurrentLibrary.unbind(d.el, d.event, d.listener)
            }
            Gb = {}
        };
        this.registerListener = function (a, b, c) {
            y.CurrentLibrary.bind(a, b, c),
            jsPlumbUtil.addToList(Gb, b, {
                el: a,
                event: b,
                listener: c
            })
        },
        this.unregisterListener = function (a, b, c) {
            y.CurrentLibrary.unbind(a, b, c),
            jsPlumbUtil.removeWithFunction(Gb,
            function (a) {
                return a.type == b && a.listener == c
            })
        },
        this.reset = function () {
            j.deleteEveryEndpoint(),
            j.unbind(),
            sb = {},
            tb = {},
            ub = {},
            vb = {},
            xb = {},
            yb = {},
            zb = {},
            Cb = {},
            t.splice(0),
            Hb(),
            j.anchorManager.reset(),
            jsPlumbAdapter.headless || j.dragManager.reset()
        },
        this.setDefaultScope = function (a) {
            return G = a,
            j
        },
        this.setDraggable = X,
        this.setId = function (a, b, c) {
            var d;
            jsPlumbUtil.isString(a) ? d = a : (a = e(a), d = j.getId(a));
            var f = j.getConnections({
                source: d,
                scope: "*"
            },
            !0),
            g = j.getConnections({
                target: d,
                scope: "*"
            },
            !0);
            b = "" + b,
            c ? a = e(b) : (a = e(d), jsPlumbAdapter.setAttribute(a, "id", b)),
            u[b] = u[d] || [];
            for (var h = 0,
            i = u[b].length; i > h; h++) u[b][h].setElementId(b),
            u[b][h].setReferenceElement(a);
            delete u[d],
            j.anchorManager.changeId(d, b),
            jsPlumbAdapter.headless || j.dragManager.changeId(d, b);
            var k = function (c, d, e) {
                for (var f = 0,
                g = c.length; g > f; f++) c[f].endpoints[d].setElementId(b),
                c[f].endpoints[d].setReferenceElement(a),
                c[f][e + "Id"] = b,
                c[f][e] = a
            };
            k(f, 0, "source"),
            k(g, 1, "target"),
            j.repaint(b)
        },
        this.setDebugLog = function (a) {
            r = a
        },
        this.setSuspendDrawing = function (a, b) {
            var c = E;
            return E = a,
            F = a ? (new Date).getTime() : null,
            b && j.repaintEverything(),
            c
        },
        this.isSuspendDrawing = function () {
            return E
        },
        this.getSuspendedAt = function () {
            return F
        },
        this.doWhileSuspended = function (b, c) {
            var d = j.isSuspendDrawing();
            d || j.setSuspendDrawing(!0);
            try {
                b()
            } catch (e) {
                a.log("Function run while suspended failed", e)
            }
            d || j.setSuspendDrawing(!1, !c)
        },
        this.updateOffset = _,
        this.getOffset = function (a) {
            return x[a]
        },
        this.getSize = function (a) {
            return D[a]
        },
        this.getCachedData = ab,
        this.timestamp = h,
        this.setRenderMode = function (a) {
            H = jsPlumbAdapter.setRenderMode(a);
            var b, c;
            if (H == y.CANVAS) {
                var d = function (a) {
                    y.CurrentLibrary.bind(document, a,
                    function (d) {
                        if (!j.currentlyDragging && H == y.CANVAS) {
                            for (b = 0, c = t.length; c > b; b++) {
                                var e = t[b].getConnector()[a](d);
                                if (e) return
                            }
                            for (var f in u) {
                                var g = u[f];
                                for (b = 0, c = g.length; c > b; b++) if (g[b].endpoint[a] && g[b].endpoint[a](d)) return
                            }
                        }
                    })
                };
                d("click"),
                d("dblclick"),
                d("mousemove"),
                d("mousedown"),
                d("mouseup"),
                d("contextmenu")
            }
            return H
        },
        this.getRenderMode = function () {
            return H
        },
        this.show = function (a, b) {
            return Y(a, "block", b),
            j
        },
        this.getTestHarness = function () {
            return {
                endpointsByElement: u,
                endpointCount: function (a) {
                    var b = u[a];
                    return b ? b.length : 0
                },
                connectionCount: function (a) {
                    a = a || G;
                    var b = j.getConnections({
                        scope: a
                    });
                    return b ? b.length : 0
                },
                getId: bb,
                makeAnchor: self.makeAnchor,
                makeDynamicAnchor: self.makeDynamicAnchor
            }
        },
        this.toggleVisible = $,
        this.toggleDraggable = Z,
        this.addListener = this.bind,
        this.adjustForParentOffsetAndScroll = function (a, b) {
            var c = null,
            d = a;
            if ("svg" === b.tagName.toLowerCase() && b.parentNode ? c = b.parentNode : b.offsetParent && (c = b.offsetParent), null != c) {
                var e = "body" === c.tagName.toLowerCase() ? {
                    left: 0,
                    top: 0
                } : f(c, j),
                g = "body" === c.tagName.toLowerCase() ? {
                    left: 0,
                    top: 0
                } : {
                    left: c.scrollLeft,
                    top: c.scrollTop
                };
                d[0] = a[0] - e.left + g.left,
                d[1] = a[1] - e.top + g.top
            }
            return d
        },
        jsPlumbAdapter.headless || (j.dragManager = jsPlumbAdapter.getDragManager(j), j.recalculateOffsets = j.dragManager.updateOffsets)
    };
    jsPlumbUtil.extend(x, jsPlumbUtil.EventGenerator, {
        setAttribute: function (a, b, c) {
            jsPlumbAdapter.setAttribute(a, b, c)
        },
        getAttribute: function (a, b) {
            return jsPlumbAdapter.getAttribute(y.CurrentLibrary.getDOMElement(a), b)
        },
        registerConnectionType: function (a, b) {
            this._connectionTypes[a] = y.extend({},
            b)
        },
        registerConnectionTypes: function (a) {
            for (var b in a) this._connectionTypes[b] = y.extend({},
            a[b])
        },
        registerEndpointType: function (a, b) {
            this._endpointTypes[a] = y.extend({},
            b)
        },
        registerEndpointTypes: function (a) {
            for (var b in a) this._endpointTypes[b] = y.extend({},
            a[b])
        },
        getType: function (a, b) {
            return "connection" === b ? this._connectionTypes[a] : this._endpointTypes[a]
        },
        setIdChanged: function (a, b) {
            this.setId(a, b, !0)
        }
    });
    var y = new x;
    "undefined" != typeof window && (window.jsPlumb = y),
    y.getInstance = function (a) {
        var b = new x(a);
        return b.init(),
        b
    },
    "function" == typeof define && (define("jsplumb", [],
    function () {
        return y
    }), define("jsplumbinstance", [],
    function () {
        return y.getInstance()
    })),
    "undefined" != typeof exports && (exports.jsPlumb = y)
}(),
function () {
    var a = function (a, b) {
        var c = !1;
        return {
            drag: function () {
                if (c) return c = !1,
                !0;
                var d = jsPlumb.CurrentLibrary.getUIPosition(arguments, b.getZoom());
                a.element && (jsPlumb.CurrentLibrary.setOffset(a.element, d), b.repaint(a.element, d))
            },
            stopDrag: function () {
                c = !0
            }
        }
    },
    b = function (a, b, c) {
        var d = document.createElement("div");
        d.style.position = "absolute",
        jsPlumb.CurrentLibrary.getElementObject(d),
        jsPlumb.CurrentLibrary.appendElement(d, b);
        var e = c.getId(d);
        c.updateOffset({
            elId: e
        }),
        a.id = e,
        a.element = d
    },
    c = function (a, b, c, d, e, f, g) {
        var h = new jsPlumb.FloatingAnchor({
            reference: b,
            referenceCanvas: d,
            jsPlumbInstance: f
        });
        return g({
            paintStyle: a,
            endpoint: c,
            anchor: h,
            source: e,
            scope: "__floating"
        })
    },
    d = ["connectorStyle", "connectorHoverStyle", "connectorOverlays", "connector", "connectionType", "connectorClass", "connectorHoverClass"],
    e = function (a, b) {
        var c = 0;
        if (null != b) for (var d = 0; d < a.connections.length; d++) if (a.connections[d].sourceId == b || a.connections[d].targetId == b) {
            c = d;
            break
        }
        return a.connections[c]
    },
    f = function (a, b) {
        return jsPlumbUtil.findWithFunction(b.connections,
        function (b) {
            return b.id == a.id
        })
    };
    jsPlumb.Endpoint = function (g) {
        var h = g._jsPlumb,
        i = jsPlumb.CurrentLibrary,
        j = (jsPlumbAdapter.getAttribute, i.getElementObject),
        k = i.getDOMElement,
        l = jsPlumbUtil,
        m = g.newConnection,
        n = g.newEndpoint,
        o = g.finaliseConnection,
        p = g.fireDetachEvent,
        q = g.floatingConnections;
        this.idPrefix = "_jsplumb_e_",
        this.defaultLabelLocation = [.5, .5],
        this.defaultOverlayKeys = ["Overlays", "EndpointOverlays"],
        this.parent = g.parent,
        OverlayCapableJsPlumbUIComponent.apply(this, arguments),
        this.getDefaultType = function () {
            return {
                parameters: {},
                scope: null,
                maxConnections: this._jsPlumb.instance.Defaults.MaxConnections,
                paintStyle: this._jsPlumb.instance.Defaults.EndpointStyle || jsPlumb.Defaults.EndpointStyle,
                endpoint: this._jsPlumb.instance.Defaults.Endpoint || jsPlumb.Defaults.Endpoint,
                hoverPaintStyle: this._jsPlumb.instance.Defaults.EndpointHoverStyle || jsPlumb.Defaults.EndpointHoverStyle,
                overlays: this._jsPlumb.instance.Defaults.EndpointOverlays || jsPlumb.Defaults.EndpointOverlays,
                connectorStyle: g.connectorStyle,
                connectorHoverStyle: g.connectorHoverStyle,
                connectorClass: g.connectorClass,
                connectorHoverClass: g.connectorHoverClass,
                connectorOverlays: g.connectorOverlays,
                connector: g.connector,
                connectorTooltip: g.connectorTooltip
            }
        },
        this._jsPlumb.enabled = !(g.enabled === !1),
        this._jsPlumb.visible = !0,
        this.element = k(g.source),
        this._jsPlumb.uuid = g.uuid,
        this._jsPlumb.floatingEndpoint = null;
        var r = null;
        this._jsPlumb.uuid && (g.endpointsByUUID[this._jsPlumb.uuid] = this),
        this.elementId = g.elementId,
        this._jsPlumb.connectionCost = g.connectionCost,
        this._jsPlumb.connectionsDirected = g.connectionsDirected,
        this._jsPlumb.currentAnchorClass = "",
        this._jsPlumb.events = {};
        var s = function () {
            i.removeClass(this.element, h.endpointAnchorClassPrefix + "_" + this._jsPlumb.currentAnchorClass),
            this.removeClass(h.endpointAnchorClassPrefix + "_" + this._jsPlumb.currentAnchorClass),
            this._jsPlumb.currentAnchorClass = this.anchor.getCssClass(),
            this.addClass(h.endpointAnchorClassPrefix + "_" + this._jsPlumb.currentAnchorClass),
            i.addClass(this.element, h.endpointAnchorClassPrefix + "_" + this._jsPlumb.currentAnchorClass)
        }.bind(this);
        this.setAnchor = function (a, b) {
            return this._jsPlumb.instance.continuousAnchorFactory.clear(this.elementId),
            this.anchor = this._jsPlumb.instance.makeAnchor(a, this.elementId, h),
            s(),
            this.anchor.bind("anchorChanged",
            function (a) {
                this.fire("anchorChanged", {
                    endpoint: this,
                    anchor: a
                }),
                s()
            }.bind(this)),
            b || this._jsPlumb.instance.repaint(this.elementId),
            this
        };
        var t = g.anchor ? g.anchor : g.anchors ? g.anchors : h.Defaults.Anchor || "Top";
        this.setAnchor(t, !0);
        var u = function (a) {
            this.connections.length > 0 ? this.connections[0].setHover(a, !1) : this.setHover(a)
        }.bind(this);
        if (g._transient || this._jsPlumb.instance.anchorManager.add(this, this.elementId), this.setEndpoint = function (a) {
            null != this.endpoint && (this.endpoint.cleanup(), this.endpoint.destroy());
            var b = function (a, b) {
                var c = h.getRenderMode();
                if (jsPlumb.Endpoints[c][a]) return new jsPlumb.Endpoints[c][a](b);
                if (!h.Defaults.DoNotThrowErrors) throw {
            msg: "jsPlumb: unknown endpoint type '" + a + "'"
        }
        },
            c = {
            _jsPlumb: this._jsPlumb.instance,
            cssClass: g.cssClass,
            parent: g.parent,
            container: g.container,
            tooltip: g.tooltip,
            connectorTooltip: g.connectorTooltip,
            endpoint: this
        };
            l.isString(a) ? this.endpoint = b(a, c) : l.isArray(a) ? (c = l.merge(a[1], c), this.endpoint = b(a[0], c)) : this.endpoint = a.clone(),
            jsPlumb.extend({},
            c),
            this.endpoint.clone = function () {
                return l.isString(a) ? b(a, c) : l.isArray(a) ? (c = l.merge(a[1], c), b(a[0], c)) : void 0
        }.bind(this),
            this.type = this.endpoint.type,
            this.bindListeners(this.endpoint, this, u)
        },
        this.setEndpoint(g.endpoint || h.Defaults.Endpoint || jsPlumb.Defaults.Endpoint || "Dot"), this.setPaintStyle(g.paintStyle || g.style || h.Defaults.EndpointStyle || jsPlumb.Defaults.EndpointStyle, !0), this.setHoverPaintStyle(g.hoverPaintStyle || h.Defaults.EndpointHoverStyle || jsPlumb.Defaults.EndpointHoverStyle, !0), this._jsPlumb.paintStyleInUse = this.getPaintStyle(), l.copyValues(d, g, this), this.isSource = g.isSource || !1, this.isTarget = g.isTarget || !1, this._jsPlumb.maxConnections = g.maxConnections || h.Defaults.MaxConnections, this.canvas = this.endpoint.canvas, this.addClass(h.endpointAnchorClassPrefix + "_" + this._jsPlumb.currentAnchorClass), i.addClass(this.element, h.endpointAnchorClassPrefix + "_" + this._jsPlumb.currentAnchorClass), this.connections = g.connections || [], this.connectorPointerEvents = g["connector-pointer-events"], this.scope = g.scope || h.getDefaultScope(), this.timestamp = null, this.reattachConnections = g.reattach || h.Defaults.ReattachConnections, this.connectionsDetachable = h.Defaults.ConnectionsDetachable, (g.connectionsDetachable === !1 || g.detachable === !1) && (this.connectionsDetachable = !1), this.dragAllowedWhenFull = g.dragAllowedWhenFull || !0, g.onMaxConnections && this.bind("maxConnections", g.onMaxConnections), this.addConnection = function (a) {
            this.connections.push(a),
            this[(this.connections.length > 0 ? "add" : "remove") + "Class"](h.endpointConnectedClass),
            this[(this.isFull() ? "add" : "remove") + "Class"](h.endpointFullClass)
        },
        this.detachFromConnection = function (a, b) {
            b = null == b ? f(a, this) : b,
            b >= 0 && (this.connections.splice(b, 1), this[(this.connections.length > 0 ? "add" : "remove") + "Class"](h.endpointConnectedClass), this[(this.isFull() ? "add" : "remove") + "Class"](h.endpointFullClass))
        },
        this.detach = function (a, b, c, d, e, g, i) {
            var j = null == i ? f(a, this) : i,
            k = !1;
            return d = d !== !1,
            j >= 0 && (c || a._forceDetach || a.isDetachable() && a.isDetachAllowed(a) && this.isDetachAllowed(a)) && (h.deleteObject({
            connection: a,
            fireEvent: !b && d,
            originalEvent: e
        }), k = !0),
            k
        },
        this.detachAll = function (a, b) {
            for (; this.connections.length > 0;) this.detach(this.connections[0], !1, !0, a !== !1, b, this, 0);
            return this
        },
        this.detachFrom = function (a, b, c) {
            for (var d = [], e = 0; e < this.connections.length; e++) (this.connections[e].endpoints[1] == a || this.connections[e].endpoints[0] == a) && d.push(this.connections[e]);
            for (var f = 0; f < d.length; f++) this.detach(d[f], !1, !0, b, c);
            return this
        },
        this.getElement = function () {
            return this.element
        },
        this.setElement = function (a) {
            var b = this._jsPlumb.instance.getId(a),
            c = this.elementId;
            return l.removeWithFunction(g.endpointsByElement[this.elementId],
            function (a) {
                return a.id == this.id
        }.bind(this)),
            this.element = k(a),
            this.elementId = h.getId(this.element),
            h.anchorManager.rehomeEndpoint(this, c, this.element),
            h.dragManager.endpointAdded(this.element),
            l.addToList(g.endpointsByElement, b, this),
            this
        },
        this.makeInPlaceCopy = function () {
            var a = this.anchor.getCurrentLocation({
            element: this
        }),
            b = this.anchor.getOrientation(this),
            c = this.anchor.getCssClass(),
            d = {
            bind: function () { },
            compute: function () {
                    return [a[0], a[1]]
        },
            getCurrentLocation: function () {
                    return [a[0], a[1]]
        },
            getOrientation: function () {
                    return b
        },
            getCssClass: function () {
                    return c
        }
        };
            return n({
            anchor: d,
            source: this.element,
            paintStyle: this.getPaintStyle(),
            endpoint: g.hideOnDrag ? "Blank" : this.endpoint,
            _transient: !0,
            scope: this.scope
        })
        },
        this.isFloating = function () {
            return null != this.anchor && this.anchor.isFloating
        },
        this.connectorSelector = function () {
            var a = this.connections[0];
            return this.isTarget && a ? a : this.connections.length < this._jsPlumb.maxConnections || -1 == this._jsPlumb.maxConnections ? null : a
        },
        this.setStyle = this.setPaintStyle, this.paint = function (a) {
            a = a || {};
            var b = a.timestamp,
            c = !(a.recalc === !1);
            if (!b || this.timestamp !== b) {
                var d = h.updateOffset({
            elId: this.elementId,
            timestamp: b
        }),
                f = a.offset ? a.offset.o : d.o;
                if (null != f) {
                    var g = a.anchorPoint,
                    i = a.connectorPaintStyle;
                    if (null == g) {
                        var j = a.dimensions || d.s,
                        k = {
            xy: [f.left, f.top],
            wh: j,
            element: this,
            timestamp: b
        };
                        if (c && this.anchor.isDynamic && this.connections.length > 0) {
                            var l = e(this, a.elementWithPrecedence),
                            m = l.endpoints[0] == this ? 1 : 0,
                            n = 0 === m ? l.sourceId : l.targetId,
                            o = h.getCachedData(n),
                            p = o.o,
                            q = o.s;
                            k.txy = [p.left, p.top],
                            k.twh = q,
                            k.tElement = l.endpoints[m]
        }
                        g = this.anchor.compute(k)
        }
                    this.endpoint.compute(g, this.anchor.getOrientation(this), this._jsPlumb.paintStyleInUse, i || this.paintStyleInUse),
                    this.endpoint.paint(this._jsPlumb.paintStyleInUse, this.anchor),
                    this.timestamp = b;
                    for (var r = 0; r < this._jsPlumb.overlays.length; r++) {
                        var s = this._jsPlumb.overlays[r];
                        s.isVisible() && (this._jsPlumb.overlayPlacements[r] = s.draw(this.endpoint, this._jsPlumb.paintStyleInUse), s.paint(this._jsPlumb.overlayPlacements[r]))
        }
        }
        }
        },
        this.repaint = this.paint, i.isDragSupported(this.element) && (this.isSource || this.isTarget)) {
            var v = {
                id: null,
                element: null
            },
            w = null,
            x = !1,
            y = null,
            z = a(v, h),
            A = function () {
                w = this.connectorSelector();
                var a = !0;
                if (this.isEnabled() || (a = !1), null != w || this.isSource || (a = !1), this.isSource && this.isFull() && !this.dragAllowedWhenFull && (a = !1), null == w || w.isDetachable() || (a = !1), a === !1) return i.stopDrag && i.stopDrag(),
                z.stopDrag(),
                !1;
                for (var d = 0; d < this.connections.length; d++) this.connections[d].setHover(!1);
                this.addClass("endpointDrag"),
                h.setConnectionBeingDragged(!0),
                w && !this.isFull() && this.isSource && (w = null),
                h.updateOffset({
                    elId: this.elementId
                }),
                r = this.makeInPlaceCopy(),
                r.referenceEndpoint = this,
                r.paint(),
                b(v, this.parent, h);
                var e = j(r.canvas),
                f = jsPlumb.CurrentLibrary.getOffset(e, h),
                k = h.adjustForParentOffsetAndScroll([f.left, f.top], r.canvas),
                o = j(this.canvas);
                if (i.setOffset(v.element, {
                    left: k[0],
                    top: k[1]
                }), this.parentAnchor && (this.anchor = h.makeAnchor(this.parentAnchor, this.elementId, h)), h.setAttribute(this.canvas, "dragId", v.id), h.setAttribute(this.canvas, "elId", this.elementId), this._jsPlumb.floatingEndpoint = c(this.getPaintStyle(), this.anchor, this.endpoint, this.canvas, v.element, h, n), this.canvas.style.visibility = "hidden", null == w) this.anchor.locked = !0,
                this.setHover(!1, !1),
                w = m({
                    sourceEndpoint: this,
                    targetEndpoint: this._jsPlumb.floatingEndpoint,
                    source: this.endpointWillMoveTo || this.element,
                    target: v.element,
                    anchors: [this.anchor, this._jsPlumb.floatingEndpoint.anchor],
                    paintStyle: g.connectorStyle,
                    hoverPaintStyle: g.connectorHoverStyle,
                    connector: g.connector,
                    overlays: g.connectorOverlays,
                    type: this.connectionType,
                    cssClass: this.connectorClass,
                    hoverClass: this.connectorHoverClass
                }),
                w.addClass(h.draggingClass),
                this._jsPlumb.floatingEndpoint.addClass(h.draggingClass),
                h.fire("connectionDrag", w);
                else {
                    x = !0,
                    w.setHover(!1),
                    H(e, !1, !0);
                    var p = w.endpoints[0].id == this.id ? 0 : 1;
                    w.floatingAnchorIndex = p,
                    this.detachFromConnection(w);
                    var s = jsPlumb.CurrentLibrary.getDragScope(o);
                    h.setAttribute(this.canvas, "originalScope", s);
                    var t = i.getDropScope(o);
                    i.setDragScope(o, t),
                    h.fire("connectionDrag", w),
                    0 === p ? (y = [w.source, w.sourceId, o, s], w.source = v.element, w.sourceId = v.id) : (y = [w.target, w.targetId, o, s], w.target = v.element, w.targetId = v.id),
                    w.endpoints[0 === p ? 1 : 0].anchor.locked = !0,
                    w.suspendedEndpoint = w.endpoints[p],
                    w.suspendedElement = w.endpoints[p].getElement(),
                    w.suspendedElementId = w.endpoints[p].elementId,
                    w.suspendedElementType = 0 === p ? "source" : "target",
                    w.suspendedEndpoint.setHover(!1),
                    this._jsPlumb.floatingEndpoint.referenceEndpoint = w.suspendedEndpoint,
                    w.endpoints[p] = this._jsPlumb.floatingEndpoint,
                    w.addClass(h.draggingClass),
                    this._jsPlumb.floatingEndpoint.addClass(h.draggingClass)
                }
                q[v.id] = w,
                h.anchorManager.addFloatingConnection(v.id, w),
                l.addToList(g.endpointsByElement, v.id, this._jsPlumb.floatingEndpoint),
                h.currentlyDragging = !0
            }.bind(this),
            B = g.dragOptions || {},
            C = jsPlumb.extend({},
            i.defaultDragOptions),
            D = i.dragEvents.start,
            E = i.dragEvents.stop,
            F = i.dragEvents.drag;
            B = jsPlumb.extend(C, B),
            B.scope = B.scope || this.scope,
            B[D] = l.wrap(B[D], A, !1),
            B[F] = l.wrap(B[F], z.drag),
            B[E] = l.wrap(B[E],
            function () {
                h.setConnectionBeingDragged(!1);
                var a = i.getDropEvent(arguments),
                b = null == w.floatingAnchorIndex ? 1 : w.floatingAnchorIndex;
                w.endpoints[0 === b ? 1 : 0].anchor.locked = !1,
                w.removeClass(h.draggingClass),
                w.endpoints[b] == this._jsPlumb.floatingEndpoint && x && w.suspendedEndpoint && (0 === b ? (w.source = y[0], w.sourceId = y[1]) : (w.target = y[0], w.targetId = y[1]), i.setDragScope(y[2], y[3]), w.endpoints[b] = w.suspendedEndpoint, (w.isReattach() || w._forceReattach || w._forceDetach || !w.endpoints[0 === b ? 1 : 0].detach(w, !1, !1, !0, a)) && (w.setHover(!1), w.floatingAnchorIndex = null, w._forceDetach = null, w._forceReattach = null, this._jsPlumb.floatingEndpoint.detachFromConnection(w), w.suspendedEndpoint.addConnection(w), h.repaint(y[1]))),
                h.remove(v.element, !1),
                h.remove(r.canvas, !1),
                this.deleteAfterDragStop ? h.deleteObject({
                    endpoint: this
                }) : this._jsPlumb && (this._jsPlumb.floatingEndpoint = null, this.canvas.style.visibility = "visible", this.anchor.locked = !1, this.paint({
                    recalc: !1
                })),
                h.fire("connectionDragStop", w),
                h.currentlyDragging = !1,
                w = null
            }.bind(this));
            var G = j(this.canvas);
            i.initDraggable(G, B, !0, h)
        }
        var H = function (a, b, c, d) {
            if ((this.isTarget || b) && i.isDropSupported(this.element)) {
                var e = g.dropOptions || h.Defaults.DropOptions || jsPlumb.Defaults.DropOptions;
                e = jsPlumb.extend({},
                e),
                e.scope = e.scope || this.scope;
                var f = i.dragEvents.drop,
                k = i.dragEvents.over,
                m = i.dragEvents.out,
                n = function () {
                    this.removeClass(h.endpointDropAllowedClass),
                    this.removeClass(h.endpointDropForbiddenClass);
                    var a = i.getDropEvent(arguments),
                    b = j(i.getDragObject(arguments)),
                    c = h.getAttribute(b, "dragId"),
                    e = (h.getAttribute(b, "elId"), h.getAttribute(b, "originalScope")),
                    f = q[c],
                    g = f.suspendedEndpoint && (f.suspendedEndpoint.id == this.id || this.referenceEndpoint && f.suspendedEndpoint.id == this.referenceEndpoint.id);
                    if (g) return f._forceReattach = !0,
                    void 0;
                    if (null != f) {
                        var k = null == f.floatingAnchorIndex ? 1 : f.floatingAnchorIndex;
                        e && jsPlumb.CurrentLibrary.setDragScope(b, e);
                        var l = null != d ? d.isEnabled() : !0;
                        if (this.isFull() && this.fire("maxConnections", {
                            endpoint: this,
                            connection: f,
                            maxConnections: this._jsPlumb.maxConnections
                        },
                        a), !this.isFull() && (0 !== k || this.isSource) && (1 != k || this.isTarget) && l) {
                            var m = !0;
                            f.suspendedEndpoint && f.suspendedEndpoint.id != this.id && (0 === k ? (f.source = f.suspendedEndpoint.element, f.sourceId = f.suspendedEndpoint.elementId) : (f.target = f.suspendedEndpoint.element, f.targetId = f.suspendedEndpoint.elementId), f.isDetachAllowed(f) && f.endpoints[k].isDetachAllowed(f) && f.suspendedEndpoint.isDetachAllowed(f) && h.checkCondition("beforeDetach", f) || (m = !1)),
                            0 === k ? (f.source = this.element, f.sourceId = this.elementId) : (f.target = this.element, f.targetId = this.elementId);
                            var n = function () {
                                f.floatingAnchorIndex = null
                            },
                            r = function () {
                                f.endpoints[k].detachFromConnection(f),
                                f.suspendedEndpoint && f.suspendedEndpoint.detachFromConnection(f),
                                f.endpoints[k] = this,
                                this.addConnection(f);
                                var b = this.getParameters();
                                for (var c in b) f.setParameter(c, b[c]);
                                if (f.suspendedEndpoint) {
                                    var d = f.suspendedEndpoint.getElement(),
                                    e = f.suspendedEndpoint.elementId;
                                    p({
                                        source: 0 === k ? d : f.source,
                                        target: 1 == k ? d : f.target,
                                        sourceId: 0 === k ? e : f.sourceId,
                                        targetId: 1 == k ? e : f.targetId,
                                        sourceEndpoint: 0 === k ? f.suspendedEndpoint : f.endpoints[0],
                                        targetEndpoint: 1 == k ? f.suspendedEndpoint : f.endpoints[1],
                                        connection: f
                                    },
                                    !0, a)
                                } else b.draggable && jsPlumb.CurrentLibrary.initDraggable(this.element, B, !0, h);
                                1 == k ? h.anchorManager.updateOtherEndpoint(f.sourceId, f.suspendedElementId, f.targetId, f) : h.anchorManager.sourceChanged(f.suspendedEndpoint.elementId, f.sourceId, f),
                                o(f, null, a, !0),
                                n()
                            }.bind(this),
                            s = function () {
                                f.suspendedEndpoint && (f.endpoints[k] = f.suspendedEndpoint, f.setHover(!1), f._forceDetach = !0, 0 === k ? (f.source = f.suspendedEndpoint.element, f.sourceId = f.suspendedEndpoint.elementId) : (f.target = f.suspendedEndpoint.element, f.targetId = f.suspendedEndpoint.elementId), f.suspendedEndpoint.addConnection(f), f.endpoints[0].repaint(), f.repaint(), h.repaint(f.sourceId), f._forceDetach = !1),
                                n()
                            };
                            m = m && this.isDropAllowed(f.sourceId, f.targetId, f.scope, f, this),
                            m ? r() : s()
                        }
                        h.currentlyDragging = !1
                    }
                }.bind(this);
                e[f] = l.wrap(e[f], n),
                e[k] = l.wrap(e[k],
                function () {
                    var a = i.getDragObject(arguments),
                    b = h.getAttribute(a, "dragId"),
                    c = q[b];
                    if (null != c) {
                        var d = null == c.floatingAnchorIndex ? 1 : c.floatingAnchorIndex,
                        e = this.isTarget && 0 !== c.floatingAnchorIndex || c.suspendedEndpoint && this.referenceEndpoint && this.referenceEndpoint.id == c.suspendedEndpoint.id;
                        if (e) {
                            var f = h.checkCondition("checkDropAllowed", {
                                sourceEndpoint: c.endpoints[d],
                                targetEndpoint: this,
                                connection: c
                            });
                            this[(f ? "add" : "remove") + "Class"](h.endpointDropAllowedClass),
                            this[(f ? "remove" : "add") + "Class"](h.endpointDropForbiddenClass),
                            c.endpoints[d].anchor.over(this.anchor, this)
                        }
                    }
                }.bind(this)),
                e[m] = l.wrap(e[m],
                function () {
                    var a = i.getDragObject(arguments),
                    b = h.getAttribute(a, "dragId"),
                    c = q[b];
                    if (null != c) {
                        var d = null == c.floatingAnchorIndex ? 1 : c.floatingAnchorIndex,
                        e = this.isTarget && 0 !== c.floatingAnchorIndex || c.suspendedEndpoint && this.referenceEndpoint && this.referenceEndpoint.id == c.suspendedEndpoint.id;
                        e && (this.removeClass(h.endpointDropAllowedClass), this.removeClass(h.endpointDropForbiddenClass), c.endpoints[d].anchor.out())
                    }
                }.bind(this)),
                i.initDroppable(a, e, !0, c)
            }
        }.bind(this);
        return H(j(this.canvas), !0, !(g._transient || this.anchor.isFloating), this),
        g.type && this.addType(g.type, g.data, h.isSuspendDrawing()),
        this
    },
    jsPlumbUtil.extend(jsPlumb.Endpoint, OverlayCapableJsPlumbUIComponent, {
        getTypeDescriptor: function () {
            return "endpoint"
        },
        isVisible: function () {
            return this._jsPlumb.visible
        },
        setVisible: function (a, b, c) {
            if (this._jsPlumb.visible = a, this.canvas && (this.canvas.style.display = a ? "block" : "none"), this[a ? "showOverlays" : "hideOverlays"](), !b) for (var d = 0; d < this.connections.length; d++) if (this.connections[d].setVisible(a), !c) {
                var e = this === this.connections[d].endpoints[0] ? 1 : 0;
                1 == this.connections[d].endpoints[e].connections.length && this.connections[d].endpoints[e].setVisible(a, !0, !0)
            }
        },
        getAttachedElements: function () {
            return this.connections
        },
        applyType: function (a) {
            null != a.maxConnections && (this._jsPlumb.maxConnections = a.maxConnections),
            a.scope && (this.scope = a.scope),
            jsPlumbUtil.copyValues(d, a, this)
        },
        isEnabled: function () {
            return this._jsPlumb.enabled
        },
        setEnabled: function (a) {
            this._jsPlumb.enabled = a
        },
        cleanup: function () {
            jsPlumb.CurrentLibrary.removeClass(this.element, this._jsPlumb.instance.endpointAnchorClassPrefix + "_" + this._jsPlumb.currentAnchorClass),
            this.anchor = null,
            this.endpoint.cleanup(),
            this.endpoint.destroy(),
            this.endpoint = null;
            var a = jsPlumb.CurrentLibrary.getElementObject(this.canvas);
            jsPlumb.CurrentLibrary.destroyDraggable(a),
            jsPlumb.CurrentLibrary.destroyDroppable(a)
        },
        setHover: function (a) {
            this.endpoint && this._jsPlumb && !this._jsPlumb.instance.isConnectionBeingDragged() && this.endpoint.setHover(a)
        },
        isFull: function () {
            return !(this.isFloating() || this._jsPlumb.maxConnections < 1 || this.connections.length < this._jsPlumb.maxConnections)
        },
        getConnectionCost: function () {
            return this._jsPlumb.connectionCost
        },
        setConnectionCost: function (a) {
            this._jsPlumb.connectionCost = a
        },
        areConnectionsDirected: function () {
            return this._jsPlumb.connectionsDirected
        },
        setConnectionsDirected: function (a) {
            this._jsPlumb.connectionsDirected = a
        },
        setElementId: function (a) {
            this.elementId = a,
            this.anchor.elementId = a
        },
        setReferenceElement: function (a) {
            this.element = jsPlumb.CurrentLibrary.getDOMElement(a)
        },
        setDragAllowedWhenFull: function (a) {
            this.dragAllowedWhenFull = a
        },
        equals: function (a) {
            return this.anchor.equals(a.anchor)
        },
        getUuid: function () {
            return this._jsPlumb.uuid
        },
        computeAnchor: function (a) {
            return this.anchor.compute(a)
        }
    })
}(),
function () {
    var a = function (a, b, c, d) {
        if (!a.Defaults.DoNotThrowErrors && null == jsPlumb.Connectors[b][c]) throw {
            msg: "jsPlumb: unknown connector type '" + c + "'"
        };
        return new jsPlumb.Connectors[b][c](d)
    },
    b = function (a, b, c) {
        return a ? c.makeAnchor(a, b, c) : null
    },
    c = function (a, c, d, e, f, g, h, i, j, k) {
        var l;
        if (e) d.endpoints[f] = e,
        e.addConnection(d);
        else {
            g.endpoints || (g.endpoints = [null, null]);
            var m = g.endpoints[f] || g.endpoint || a.Defaults.Endpoints[f] || jsPlumb.Defaults.Endpoints[f] || a.Defaults.Endpoint || jsPlumb.Defaults.Endpoint;
            g.endpointStyles || (g.endpointStyles = [null, null]),
            g.endpointHoverStyles || (g.endpointHoverStyles = [null, null]);
            var n = g.endpointStyles[f] || g.endpointStyle || a.Defaults.EndpointStyles[f] || jsPlumb.Defaults.EndpointStyles[f] || a.Defaults.EndpointStyle || jsPlumb.Defaults.EndpointStyle;
            null == n.fillStyle && null != j && (n.fillStyle = j.strokeStyle),
            null == n.outlineColor && null != j && (n.outlineColor = j.outlineColor),
            null == n.outlineWidth && null != j && (n.outlineWidth = j.outlineWidth);
            var o = g.endpointHoverStyles[f] || g.endpointHoverStyle || a.Defaults.EndpointHoverStyles[f] || jsPlumb.Defaults.EndpointHoverStyles[f] || a.Defaults.EndpointHoverStyle || jsPlumb.Defaults.EndpointHoverStyle;
            null != k && (null == o && (o = {}), null == o.fillStyle && (o.fillStyle = k.strokeStyle));
            var p = g.anchors ? g.anchors[f] : g.anchor ? g.anchor : b(a.Defaults.Anchors[f], i, a) || b(jsPlumb.Defaults.Anchors[f], i, a) || b(a.Defaults.Anchor, i, a) || b(jsPlumb.Defaults.Anchor, i, a),
            q = g.uuids ? g.uuids[f] : null;
            l = c({
                paintStyle: n,
                hoverPaintStyle: o,
                endpoint: m,
                connections: [d],
                uuid: q,
                anchor: p,
                source: h,
                scope: g.scope,
                container: g.container,
                reattach: g.reattach || a.Defaults.ReattachConnections,
                detachable: g.detachable || a.Defaults.ConnectionsDetachable
            }),
            d.endpoints[f] = l,
            g.drawEndpoints === !1 && l.setVisible(!1, !0, !0)
        }
        return l
    };
    jsPlumb.Connection = function (a) {
        var b = (a.newConnection, a.newEndpoint),
        d = jsPlumb.CurrentLibrary,
        e = (d.getAttribute, d.getElementObject, d.getDOMElement),
        f = jsPlumbUtil;
        d.getOffset,
        this.connector = null,
        this.idPrefix = "_jsplumb_c_",
        this.defaultLabelLocation = .5,
        this.defaultOverlayKeys = ["Overlays", "ConnectionOverlays"],
        this.parent = a.parent,
        this.previousConnection = a.previousConnection,
        this.source = e(a.source),
        this.target = e(a.target),
        a.sourceEndpoint && (this.source = a.sourceEndpoint.endpointWillMoveTo || a.sourceEndpoint.getElement()),
        a.targetEndpoint && (this.target = a.targetEndpoint.getElement()),
        OverlayCapableJsPlumbUIComponent.apply(this, arguments),
        this.sourceId = this._jsPlumb.instance.getId(this.source),
        this.targetId = this._jsPlumb.instance.getId(this.target),
        this.scope = a.scope,
        this.endpoints = [],
        this.endpointStyles = [];
        var g = this._jsPlumb.instance;
        this._jsPlumb.visible = !0,
        this._jsPlumb.editable = a.editable === !0,
        this._jsPlumb.params = {
            parent: a.parent,
            cssClass: a.cssClass,
            container: a.container,
            "pointer-events": a["pointer-events"],
            editorParams: a.editorParams
        },
        this._jsPlumb.lastPaintedAt = null,
        this.getDefaultType = function () {
            return {
                parameters: {},
                scope: null,
                detachable: this._jsPlumb.instance.Defaults.ConnectionsDetachable,
                rettach: this._jsPlumb.instance.Defaults.ReattachConnections,
                paintStyle: this._jsPlumb.instance.Defaults.PaintStyle || jsPlumb.Defaults.PaintStyle,
                connector: this._jsPlumb.instance.Defaults.Connector || jsPlumb.Defaults.Connector,
                hoverPaintStyle: this._jsPlumb.instance.Defaults.HoverPaintStyle || jsPlumb.Defaults.HoverPaintStyle,
                overlays: this._jsPlumb.instance.Defaults.ConnectorOverlays || jsPlumb.Defaults.ConnectorOverlays
            }
        };
        var h = c(g, b, this, a.sourceEndpoint, 0, a, this.source, this.sourceId, a.paintStyle, a.hoverPaintStyle);
        h && f.addToList(a.endpointsByElement, this.sourceId, h);
        var i = c(g, b, this, a.targetEndpoint, 1, a, this.target, this.targetId, a.paintStyle, a.hoverPaintStyle);
        i && f.addToList(a.endpointsByElement, this.targetId, i),
        this.scope || (this.scope = this.endpoints[0].scope),
        null != a.deleteEndpointsOnDetach ? (this.endpoints[0]._deleteOnDetach = a.deleteEndpointsOnDetach, this.endpoints[1]._deleteOnDetach = a.deleteEndpointsOnDetach) : (this.endpoints[0]._doNotDeleteOnDetach || (this.endpoints[0]._deleteOnDetach = !0), this.endpoints[1]._doNotDeleteOnDetach || (this.endpoints[1]._deleteOnDetach = !0)),
        this.setConnector(this.endpoints[0].connector || this.endpoints[1].connector || a.connector || g.Defaults.Connector || jsPlumb.Defaults.Connector, !0),
        a.path && this.connector.setPath(a.path),
        this.setPaintStyle(this.endpoints[0].connectorStyle || this.endpoints[1].connectorStyle || a.paintStyle || g.Defaults.PaintStyle || jsPlumb.Defaults.PaintStyle, !0),
        this.setHoverPaintStyle(this.endpoints[0].connectorHoverStyle || this.endpoints[1].connectorHoverStyle || a.hoverPaintStyle || g.Defaults.HoverPaintStyle || jsPlumb.Defaults.HoverPaintStyle, !0),
        this._jsPlumb.paintStyleInUse = this.getPaintStyle();
        var j = g.getSuspendedAt();
        if (g.updateOffset({
            elId: this.sourceId,
            timestamp: j
        }), g.updateOffset({
            elId: this.targetId,
            timestamp: j
        }), !g.isSuspendDrawing()) {
            var k = g.getCachedData(this.sourceId),
            l = k.o,
            m = k.s,
            n = g.getCachedData(this.targetId),
            o = n.o,
            p = n.s,
            q = j || g.timestamp(),
            r = this.endpoints[0].anchor.compute({
                xy: [l.left, l.top],
                wh: m,
                element: this.endpoints[0],
                elementId: this.endpoints[0].elementId,
                txy: [o.left, o.top],
                twh: p,
                tElement: this.endpoints[1],
                timestamp: q
            });
            this.endpoints[0].paint({
                anchorLoc: r,
                timestamp: q
            }),
            r = this.endpoints[1].anchor.compute({
                xy: [o.left, o.top],
                wh: p,
                element: this.endpoints[1],
                elementId: this.endpoints[1].elementId,
                txy: [l.left, l.top],
                twh: m,
                tElement: this.endpoints[0],
                timestamp: q
            }),
            this.endpoints[1].paint({
                anchorLoc: r,
                timestamp: q
            })
        }
        this._jsPlumb.detachable = g.Defaults.ConnectionsDetachable,
        a.detachable === !1 && (this._jsPlumb.detachable = !1),
        this.endpoints[0].connectionsDetachable === !1 && (this._jsPlumb.detachable = !1),
        this.endpoints[1].connectionsDetachable === !1 && (this._jsPlumb.detachable = !1),
        this._jsPlumb.reattach = a.reattach || this.endpoints[0].reattachConnections || this.endpoints[1].reattachConnections || g.Defaults.ReattachConnections,
        this._jsPlumb.cost = a.cost || this.endpoints[0].getConnectionCost(),
        this._jsPlumb.directed = a.directed,
        null == a.directed && (this._jsPlumb.directed = this.endpoints[0].areConnectionsDirected());
        var s = jsPlumb.extend({},
        this.endpoints[1].getParameters());
        jsPlumb.extend(s, this.endpoints[0].getParameters()),
        jsPlumb.extend(s, this.getParameters()),
        this.setParameters(s);
        var t = a.type || this.endpoints[0].connectionType || this.endpoints[1].connectionType;
        t && this.addType(t, a.data, !0)
    },
    jsPlumbUtil.extend(jsPlumb.Connection, OverlayCapableJsPlumbUIComponent, {
        applyType: function (a, b) {
            null != a.detachable && this.setDetachable(a.detachable),
            null != a.reattach && this.setReattach(a.reattach),
            a.scope && (this.scope = a.scope),
            this.setConnector(a.connector, b)
        },
        getTypeDescriptor: function () {
            return "connection"
        },
        getAttachedElements: function () {
            return this.endpoints
        },
        addClass: function (a, b) {
            b && (this.endpoints[0].addClass(a), this.endpoints[1].addClass(a), this.suspendedEndpoint && this.suspendedEndpoint.addClass(a)),
            this.connector && this.connector.addClass(a)
        },
        removeClass: function (a, b) {
            b && (this.endpoints[0].removeClass(a), this.endpoints[1].removeClass(a), this.suspendedEndpoint && this.suspendedEndpoint.removeClass(a)),
            this.connector && this.connector.removeClass(a)
        },
        isVisible: function () {
            return this._jsPlumb.visible
        },
        setVisible: function (a) {
            this._jsPlumb.visible = a,
            this[a ? "showOverlays" : "hideOverlays"](),
            this.connector && this.connector.canvas && (this.connector.canvas.style.display = a ? "block" : "none"),
            this.repaint()
        },
        setEditable: function (a) {
            return this.connector && this.connector.isEditable() && (this._jsPlumb.editable = a),
            this._jsPlumb.editable
        },
        isEditable: function () {
            return this._jsPlumb.editable
        },
        editStarted: function () {
            this.setSuspendEvents(!0),
            this.fire("editStarted", {
                path: this.connector.getPath()
            }),
            this._jsPlumb.instance.setHoverSuspended(!0)
        },
        editCompleted: function () {
            this.fire("editCompleted", {
                path: this.connector.getPath()
            }),
            this.setSuspendEvents(!1),
            this.setHover(!1),
            this._jsPlumb.instance.setHoverSuspended(!1)
        },
        editCanceled: function () {
            this.fire("editCanceled", {
                path: this.connector.getPath()
            }),
            this.setHover(!1),
            this._jsPlumb.instance.setHoverSuspended(!1)
        },
        cleanup: function () {
            this.endpoints = null,
            this.source = null,
            this.target = null,
            null != this.connector && (this.connector.cleanup(), this.connector.destroy()),
            this.connector = null
        },
        isDetachable: function () {
            return this._jsPlumb.detachable === !0
        },
        setDetachable: function (a) {
            this._jsPlumb.detachable = a === !0
        },
        isReattach: function () {
            return this._jsPlumb.reattach === !0
        },
        setReattach: function (a) {
            this._jsPlumb.reattach = a === !0
        },
        setHover: function (a) {
            this.connector && this._jsPlumb && !this._jsPlumb.instance.isConnectionBeingDragged() && (this.connector.setHover(a), jsPlumb.CurrentLibrary[a ? "addClass" : "removeClass"](this.source, this._jsPlumb.instance.hoverSourceClass), jsPlumb.CurrentLibrary[a ? "addClass" : "removeClass"](this.target, this._jsPlumb.instance.hoverTargetClass))
        },
        getCost: function () {
            return this._jsPlumb.cost
        },
        setCost: function (a) {
            this._jsPlumb.cost = a
        },
        isDirected: function () {
            return this._jsPlumb.directed === !0
        },
        moveParent: function (a) {
            var b = jsPlumb.CurrentLibrary;
            b.getParent(this.connector.canvas),
            this.connector.bgCanvas && (b.removeElement(this.connector.bgCanvas), b.appendElement(this.connector.bgCanvas, a)),
            b.removeElement(this.connector.canvas),
            b.appendElement(this.connector.canvas, a);
            for (var c = 0; c < this._jsPlumb.overlays.length; c++) this._jsPlumb.overlays[c].isAppendedAtTopLevel && (b.removeElement(this._jsPlumb.overlays[c].canvas), b.appendElement(this._jsPlumb.overlays[c].canvas, a), this._jsPlumb.overlays[c].reattachListeners && this._jsPlumb.overlays[c].reattachListeners(this.connector));
            this.connector.reattachListeners && this.connector.reattachListeners()
        },
        getConnector: function () {
            return this.connector
        },
        setConnector: function (b, c) {
            var d = jsPlumbUtil;
            null != this.connector && (this.connector.cleanup(), this.connector.destroy());
            var e = {
                _jsPlumb: this._jsPlumb.instance,
                parent: this._jsPlumb.params.parent,
                cssClass: this._jsPlumb.params.cssClass,
                container: this._jsPlumb.params.container,
                "pointer-events": this._jsPlumb.params["pointer-events"]
            },
            f = this._jsPlumb.instance.getRenderMode();
            d.isString(b) ? this.connector = a(this._jsPlumb.instance, f, b, e) : d.isArray(b) && (this.connector = 1 == b.length ? a(this._jsPlumb.instance, f, b[0], e) : a(this._jsPlumb.instance, f, b[0], d.merge(b[1], e))),
            this.bindListeners(this.connector, this,
            function (a) {
                this.setHover(a, !1)
            }.bind(this)),
            this.canvas = this.connector.canvas,
            this._jsPlumb.editable && null != jsPlumb.ConnectorEditors && jsPlumb.ConnectorEditors[this.connector.type] && this.connector.isEditable() ? new jsPlumb.ConnectorEditors[this.connector.type]({
                connector: this.connector,
                connection: this,
                params: this._jsPlumb.params.editorParams || {}
            }) : editable = !1,
            c || this.repaint()
        },
        paint: function (a) {
            try {
                if (!this._jsPlumb.instance.isSuspendDrawing() && this._jsPlumb.visible) {
                    a = a || {};
                    var b = (a.elId, a.ui),
                    c = a.recalc,
                    d = a.timestamp,
                    e = !1,
                    f = e ? this.sourceId : this.targetId,
                    g = e ? this.targetId : this.sourceId,
                    h = e ? 0 : 1,
                    i = e ? 1 : 0;
                    if (null == d || d != this._jsPlumb.lastPaintedAt) {
                        var j = this._jsPlumb.instance.updateOffset({
                            elId: g,
                            offset: b,
                            recalc: c,
                            timestamp: d
                        }).o,
                        k = this._jsPlumb.instance.updateOffset({
                            elId: f,
                            timestamp: d
                        }).o,
                        l = this.endpoints[i],
                        m = this.endpoints[h];
                        a.clearEdits && (l.anchor.clearUserDefinedLocation(), m.anchor.clearUserDefinedLocation(), this.connector.setEdited(!1));
                        var n = l.anchor.getCurrentLocation({
                            xy: [j.left, j.top],
                            wh: [j.width, j.height],
                            element: l,
                            timestamp: d
                        }),
                        o = m.anchor.getCurrentLocation({
                            xy: [k.left, k.top],
                            wh: [k.width, k.height],
                            element: m,
                            timestamp: d
                        });
                        this.connector.resetBounds(),
                        this.connector.compute({
                            sourcePos: n,
                            targetPos: o,
                            sourceEndpoint: this.endpoints[i],
                            targetEndpoint: this.endpoints[h],
                            lineWidth: this._jsPlumb.paintStyleInUse.lineWidth,
                            sourceInfo: j,
                            targetInfo: k,
                            clearEdits: a.clearEdits === !0
                        });
                        for (var p = {
                            minX: 1 / 0,
                            minY: 1 / 0,
                            maxX: -1 / 0,
                            maxY: -1 / 0
                        },
                        q = 0; q < this._jsPlumb.overlays.length; q++) {
                            var r = this._jsPlumb.overlays[q];
                            r.isVisible() && (this._jsPlumb.overlayPlacements[q] = r.draw(this.connector, this._jsPlumb.paintStyleInUse), p.minX = Math.min(p.minX, this._jsPlumb.overlayPlacements[q].minX), p.maxX = Math.max(p.maxX, this._jsPlumb.overlayPlacements[q].maxX), p.minY = Math.min(p.minY, this._jsPlumb.overlayPlacements[q].minY), p.maxY = Math.max(p.maxY, this._jsPlumb.overlayPlacements[q].maxY))
                        }
                        var s = parseFloat(this._jsPlumb.paintStyleInUse.lineWidth || 1) / 2,
                        t = parseFloat(this._jsPlumb.paintStyleInUse.lineWidth || 0),
                        u = {
                            xmin: Math.min(this.connector.bounds.minX - (s + t), p.minX),
                            ymin: Math.min(this.connector.bounds.minY - (s + t), p.minY),
                            xmax: Math.max(this.connector.bounds.maxX + (s + t), p.maxX),
                            ymax: Math.max(this.connector.bounds.maxY + (s + t), p.maxY)
                        };
                        this.connector.paint(this._jsPlumb.paintStyleInUse, null, u);
                        for (var v = 0; v < this._jsPlumb.overlays.length; v++) {
                            var w = this._jsPlumb.overlays[v];
                            w.isVisible() && w.paint(this._jsPlumb.overlayPlacements[v], u)
                        }
                    }
                    this._jsPlumb.lastPaintedAt = d
                }
            } catch (e) { }
        },
        repaint: function (a) {
            a = a || {},
            this.paint({
                elId: this.sourceId,
                recalc: !(a.recalc === !1),
                timestamp: a.timestamp,
                clearEdits: a.clearEdits
            })
        }
    })
}(),
function () {
    jsPlumb.AnchorManager = function (a) {
        var b = {},
        c = {},
        d = {},
        e = {},
        f = {},
        g = {
            HORIZONTAL: "horizontal",
            VERTICAL: "vertical",
            DIAGONAL: "diagonal",
            IDENTITY: "identity"
        },
        h = {},
        i = this,
        j = {},
        k = a.jsPlumbInstance,
        l = jsPlumb.CurrentLibrary,
        m = {},
        n = function (a, b, c, d, e, f) {
            if (a === b) return {
                orientation: g.IDENTITY,
                a: ["top", "top"]
            };
            var h = Math.atan2(d.centery - c.centery, d.centerx - c.centerx),
            i = Math.atan2(c.centery - d.centery, c.centerx - d.centerx),
            j = c.left <= d.left && c.right >= d.left || c.left <= d.right && c.right >= d.right || c.left <= d.left && c.right >= d.right || d.left <= c.left && d.right >= c.right,
            k = c.top <= d.top && c.bottom >= d.top || c.top <= d.bottom && c.bottom >= d.bottom || c.top <= d.top && c.bottom >= d.bottom || d.top <= c.top && d.bottom >= c.bottom,
            l = function (a) {
                return [e.isContinuous ? e.verifyEdge(a[0]) : a[0], f.isContinuous ? f.verifyEdge(a[1]) : a[1]]
            },
            m = {
                orientation: g.DIAGONAL,
                theta: h,
                theta2: i
            };
            return j || k ? j ? (m.orientation = g.HORIZONTAL, m.a = c.top < d.top ? ["bottom", "top"] : ["top", "bottom"]) : (m.orientation = g.VERTICAL, m.a = c.left < d.left ? ["right", "left"] : ["left", "right"]) : d.left > c.left && d.top > c.top ? m.a = ["right", "top"] : d.left > c.left && c.top > d.top ? m.a = ["top", "left"] : d.left < c.left && d.top < c.top ? m.a = ["top", "right"] : d.left < c.left && d.top > c.top && (m.a = ["left", "top"]),
            m.a = l(m.a),
            m
        },
        o = function (a, b, c, d, e, f, g) {
            for (var h = [], i = b[e ? 0 : 1] / (d.length + 1), j = 0; j < d.length; j++) {
                var k = (j + 1) * i,
                l = f * b[e ? 1 : 0];
                g && (k = b[e ? 0 : 1] - k);
                var m = e ? k : l,
                n = c[0] + m,
                o = m / b[0],
                p = e ? l : k,
                q = c[1] + p,
                r = p / b[1];
                h.push([n, q, o, r, d[j][1], d[j][2]])
            }
            return h
        },
        p = function (a) {
            return function (b, c) {
                var d = !0;
                return d = a ? b[0][0] < c[0][0] : b[0][0] > c[0][0],
                d === !1 ? -1 : 1
            }
        },
        q = function (a, b) {
            var c = a[0][0] < 0 ? -Math.PI - a[0][0] : Math.PI - a[0][0],
            d = b[0][0] < 0 ? -Math.PI - b[0][0] : Math.PI - b[0][0];
            return c > d ? 1 : a[0][1] > b[0][1] ? 1 : -1
        },
        r = {
            top: function (a, b) {
                return a[0] > b[0] ? 1 : -1
            },
            right: p(!0),
            bottom: p(!0),
            left: q
        },
        s = function (a, b) {
            return a.sort(b)
        },
        t = function (a, b) {
            var c = k.getCachedData(a),
            e = c.s,
            g = c.o,
            h = function (b, c, e, g, h, i, j) {
                if (g.length > 0) for (var l = s(g, r[b]), m = "right" === b || "top" === b, n = o(b, c, e, l, h, i, m), p = function (a, b) {
                    var c = k.adjustForParentOffsetAndScroll([b[0], b[1]], a.canvas);
                    d[a.id] = [c[0], c[1], b[2], b[3]],
                    f[a.id] = j
                },
                q = 0; q < n.length; q++) {
                    var t = n[q][4],
                    u = t.endpoints[0].elementId === a,
                    v = t.endpoints[1].elementId === a;
                    u ? p(t.endpoints[0], n[q]) : v && p(t.endpoints[1], n[q])
                }
            };
            h("bottom", e, [g.left, g.top], b.bottom, !0, 1, [0, 1]),
            h("top", e, [g.left, g.top], b.top, !0, 0, [0, -1]),
            h("left", e, [g.left, g.top], b.left, !1, 0, [-1, 0]),
            h("right", e, [g.left, g.top], b.right, !1, 1, [1, 0])
        };
        this.reset = function () {
            b = {},
            h = {},
            j = {}
        },
        this.addFloatingConnection = function (a, b) {
            m[a] = b
        },
        this.removeFloatingConnection = function (a) {
            delete m[a]
        },
        this.newConnection = function (a) {
            var b = a.sourceId,
            c = a.targetId,
            d = a.endpoints,
            e = !0,
            f = function (a, f, g, i, j) {
                b == c && g.isContinuous && (l.removeElement(d[1].canvas), e = !1),
                jsPlumbUtil.addToList(h, i, [j, f, g.constructor == jsPlumb.DynamicAnchor])
            };
            f(0, d[0], d[0].anchor, c, a),
            e && f(1, d[1], d[1].anchor, b, a)
        };
        var u = function (a) {
            !
                function (a, b) {
                    if (a) {
                        var c = function (a) {
                            return a[4] == b
                        };
                        jsPlumbUtil.removeWithFunction(a.top, c),
                        jsPlumbUtil.removeWithFunction(a.left, c),
                        jsPlumbUtil.removeWithFunction(a.bottom, c),
                        jsPlumbUtil.removeWithFunction(a.right, c)
                    }
                }(j[a.elementId], a.id)
        };
        this.connectionDetached = function (a) {
            var b = a.connection || a,
            c = a.sourceId,
            d = a.targetId,
            e = b.endpoints,
            f = function (a, b, c, d, e) {
                null != c && c.constructor == jsPlumb.FloatingAnchor || jsPlumbUtil.removeWithFunction(h[d],
                function (a) {
                    return a[0].id == e.id
                })
            };
            f(1, e[1], e[1].anchor, c, b),
            f(0, e[0], e[0].anchor, d, b),
            u(b.endpoints[0]),
            u(b.endpoints[1]),
            i.redraw(b.sourceId),
            i.redraw(b.targetId)
        },
        this.add = function (a, c) {
            jsPlumbUtil.addToList(b, c, a)
        },
        this.changeId = function (a, c) {
            h[c] = h[a],
            b[c] = b[a],
            delete h[a],
            delete b[a]
        },
        this.getConnectionsFor = function (a) {
            return h[a] || []
        },
        this.getEndpointsFor = function (a) {
            return b[a] || []
        },
        this.deleteEndpoint = function (a) {
            jsPlumbUtil.removeWithFunction(b[a.elementId],
            function (b) {
                return b.id == a.id
            }),
            u(a)
        },
        this.clearFor = function (a) {
            delete b[a],
            b[a] = []
        };
        var v = function (b, c, d, e, f, g, h, i, j, k, l, m) {
            var n = -1,
            o = -1,
            p = e.endpoints[h],
            q = p.id,
            r = [1, 0][h],
            s = [[c, d], e, f, g, q],
            t = b[j],
            u = p._continuousAnchorEdge ? b[p._continuousAnchorEdge] : null;
            if (u) {
                var v = jsPlumbUtil.findWithFunction(u,
                function (a) {
                    return a[4] == q
                });
                if (-1 != v) {
                    u.splice(v, 1);
                    for (var w = 0; w < u.length; w++) jsPlumbUtil.addWithFunction(l, u[w][1],
                    function (a) {
                        return a.id == u[w][1].id
                    }),
                    jsPlumbUtil.addWithFunction(m, u[w][1].endpoints[h],
                    function (a) {
                        return a.id == u[w][1].endpoints[h].id
                    }),
                    jsPlumbUtil.addWithFunction(m, u[w][1].endpoints[r],
                    function (a) {
                        return a.id == u[w][1].endpoints[r].id
                    })
                }
            }
            for (w = 0; w < t.length; w++) 1 == a.idx && t[w][3] === g && -1 == o && (o = w),
            jsPlumbUtil.addWithFunction(l, t[w][1],
            function (a) {
                return a.id == t[w][1].id
            }),
            jsPlumbUtil.addWithFunction(m, t[w][1].endpoints[h],
            function (a) {
                return a.id == t[w][1].endpoints[h].id
            }),
            jsPlumbUtil.addWithFunction(m, t[w][1].endpoints[r],
            function (a) {
                return a.id == t[w][1].endpoints[r].id
            });
            if (-1 != n) t[n] = s;
            else {
                var x = i ? -1 != o ? o : 0 : t.length;
                t.splice(x, 0, s)
            }
            p._continuousAnchorEdge = j
        };
        this.updateOtherEndpoint = function (a, b, c, d) {
            var e = jsPlumbUtil.findWithFunction(h[a],
            function (a) {
                return a[0].id === d.id
            }),
            f = jsPlumbUtil.findWithFunction(h[b],
            function (a) {
                return a[0].id === d.id
            }); -1 != e && (h[a][e][0] = d, h[a][e][1] = d.endpoints[1], h[a][e][2] = d.endpoints[1].anchor.constructor == jsPlumb.DynamicAnchor),
            f > -1 && (h[b].splice(f, 1), jsPlumbUtil.addToList(h, c, [d, d.endpoints[0], d.endpoints[0].anchor.constructor == jsPlumb.DynamicAnchor]))
        },
        this.sourceChanged = function (a, b, c) {
            jsPlumbUtil.removeWithFunction(h[a],
            function (a) {
                return a[0].id === c.id
            });
            var d = jsPlumbUtil.findWithFunction(h[c.targetId],
            function (a) {
                return a[0].id === c.id
            });
            d > -1 && (h[c.targetId][d][0] = c, h[c.targetId][d][1] = c.endpoints[0], h[c.targetId][d][2] = c.endpoints[0].anchor.constructor == jsPlumb.DynamicAnchor),
            jsPlumbUtil.addToList(h, b, [c, c.endpoints[1], c.endpoints[1].anchor.constructor == jsPlumb.DynamicAnchor])
        },
        this.rehomeEndpoint = function (a, c, d) {
            var e = b[c] || [],
            f = k.getId(d);
            if (f !== c) {
                var g = jsPlumbUtil.indexOf(e, a);
                if (g > -1) {
                    var h = e.splice(g, 1)[0];
                    i.add(h, f)
                }
            }
            for (var j = 0; j < a.connections.length; j++) a.connections[j].sourceId == c ? (a.connections[j].sourceId = a.elementId, a.connections[j].source = a.element, i.sourceChanged(c, a.elementId, a.connections[j])) : a.connections[j].targetId == c && (a.connections[j].targetId = a.elementId, a.connections[j].target = a.element, i.updateOtherEndpoint(a.connections[j].sourceId, c, a.elementId, a.connections[j]))
        },
        this.redraw = function (a, c, d, e, f, g) {
            if (!k.isSuspendDrawing()) {
                var i = b[a] || [],
                l = h[a] || [],
                o = [],
                p = [],
                q = [];
                d = d || k.timestamp(),
                e = e || {
                    left: 0,
                    top: 0
                },
                c && (c = {
                    left: c.left + e.left,
                    top: c.top + e.top
                });
                for (var r = k.updateOffset({
                    elId: a,
                    offset: c,
                    recalc: !1,
                    timestamp: d
                }), s = {},
                u = 0; u < l.length; u++) {
                    var w = l[u][0],
                    x = w.sourceId,
                    y = w.targetId,
                    z = w.endpoints[0].anchor.isContinuous,
                    A = w.endpoints[1].anchor.isContinuous;
                    if (z || A) {
                        var B = x + "_" + y,
                        C = s[B],
                        D = w.sourceId == a ? 1 : 0;
                        z && !j[x] && (j[x] = {
                            top: [],
                            right: [],
                            bottom: [],
                            left: []
                        }),
                        A && !j[y] && (j[y] = {
                            top: [],
                            right: [],
                            bottom: [],
                            left: []
                        }),
                        a != y && k.updateOffset({
                            elId: y,
                            timestamp: d
                        }),
                        a != x && k.updateOffset({
                            elId: x,
                            timestamp: d
                        });
                        var E = k.getCachedData(y),
                        F = k.getCachedData(x);
                        y == x && (z || A) ? v(j[x], -Math.PI / 2, 0, w, !1, y, 0, !1, "top", x, o, p) : (C || (C = n(x, y, F.o, E.o, w.endpoints[0].anchor, w.endpoints[1].anchor), s[B] = C), z && v(j[x], C.theta, 0, w, !1, y, 0, !1, C.a[0], x, o, p), A && v(j[y], C.theta2, -1, w, !0, x, 1, !0, C.a[1], y, o, p)),
                        z && jsPlumbUtil.addWithFunction(q, x,
                        function (a) {
                            return a === x
                        }),
                        A && jsPlumbUtil.addWithFunction(q, y,
                        function (a) {
                            return a === y
                        }),
                        jsPlumbUtil.addWithFunction(o, w,
                        function (a) {
                            return a.id == w.id
                        }),
                        (z && 0 === D || A && 1 === D) && jsPlumbUtil.addWithFunction(p, w.endpoints[D],
                        function (a) {
                            return a.id == w.endpoints[D].id
                        })
                    }
                }
                for (u = 0; u < i.length; u++) 0 === i[u].connections.length && i[u].anchor.isContinuous && (j[a] || (j[a] = {
                    top: [],
                    right: [],
                    bottom: [],
                    left: []
                }), v(j[a], -Math.PI / 2, 0, {
                    endpoints: [i[u], i[u]],
                    paint: function () { }
                },
                !1, a, 0, !1, "top", a, o, p), jsPlumbUtil.addWithFunction(q, a,
                function (b) {
                    return b === a
                }));
                for (u = 0; u < q.length; u++) t(q[u], j[q[u]]);
                for (u = 0; u < i.length; u++) i[u].paint({
                    timestamp: d,
                    offset: r,
                    dimensions: r.s,
                    recalc: g !== !0
                });
                for (u = 0; u < p.length; u++) {
                    var G = k.getCachedData(p[u].elementId);
                    p[u].paint({
                        timestamp: d,
                        offset: G,
                        dimensions: G.s
                    })
                }
                for (u = 0; u < l.length; u++) {
                    var H = l[u][1];
                    if (H.anchor.constructor == jsPlumb.DynamicAnchor) {
                        H.paint({
                            elementWithPrecedence: a,
                            timestamp: d
                        }),
                        jsPlumbUtil.addWithFunction(o, l[u][0],
                        function (a) {
                            return a.id == l[u][0].id
                        });
                        for (var I = 0; I < H.connections.length; I++) H.connections[I] !== l[u][0] && jsPlumbUtil.addWithFunction(o, H.connections[I],
                        function (a) {
                            return a.id == H.connections[I].id
                        })
                    } else H.anchor.constructor == jsPlumb.Anchor && jsPlumbUtil.addWithFunction(o, l[u][0],
                    function (a) {
                        return a.id == l[u][0].id
                    })
                }
                var J = m[a];
                for (J && J.paint({
                    timestamp: d,
                    recalc: !1,
                    elId: a
                }), u = 0; u < o.length; u++) o[u].paint({
                    elId: a,
                    timestamp: d,
                    recalc: !1,
                    clearEdits: f
                })
            }
        };
        var w = function (a) {
            jsPlumbUtil.EventGenerator.apply(this),
            this.type = "Continuous",
            this.isDynamic = !0,
            this.isContinuous = !0;
            for (var b = a.faces || ["top", "right", "bottom", "left"], c = !(a.clockwise === !1), g = {},
            h = {
                top: "bottom",
                right: "left",
                left: "right",
                bottom: "top"
            },
            i = {
                top: "right",
                right: "bottom",
                left: "top",
                bottom: "left"
            },
            j = {
                top: "left",
                right: "top",
                left: "bottom",
                bottom: "right"
            },
            k = c ? i : j, l = c ? j : i, m = a.cssClass || "", n = 0; n < b.length; n++) g[b[n]] = !0;
            this.verifyEdge = function (a) {
                return g[a] ? a : g[h[a]] ? h[a] : g[k[a]] ? k[a] : g[l[a]] ? l[a] : a
            },
            this.compute = function (a) {
                return e[a.element.id] || d[a.element.id] || [0, 0]
            },
            this.getCurrentLocation = function (a) {
                return e[a.element.id] || d[a.element.id] || [0, 0]
            },
            this.getOrientation = function (a) {
                return f[a.id] || [0, 0]
            },
            this.clearUserDefinedLocation = function () {
                delete e[a.elementId]
            },
            this.setUserDefinedLocation = function (b) {
                e[a.elementId] = b
            },
            this.getCssClass = function () {
                return m
            },
            this.setCssClass = function (a) {
                m = a
            }
        };
        k.continuousAnchorFactory = {
            get: function (a) {
                var b = c[a.elementId];
                return b || (b = new w(a), c[a.elementId] = b),
                b
            },
            clear: function (a) {
                delete c[a]
            }
        }
    },
    jsPlumb.Anchor = function (a) {
        this.x = a.x || 0,
        this.y = a.y || 0,
        this.elementId = a.elementId,
        this.cssClass = a.cssClass || "",
        this.userDefinedLocation = null,
        this.orientation = a.orientation || [0, 0],
        jsPlumbUtil.EventGenerator.apply(this);
        var b = a.jsPlumbInstance;
        this.lastReturnValue = null,
        this.offsets = a.offsets || [0, 0],
        this.timestamp = null,
        this.compute = function (a) {
            var c = a.xy,
            d = a.wh,
            e = a.element,
            f = a.timestamp;
            return a.clearUserDefinedLocation && (this.userDefinedLocation = null),
            f && f === self.timestamp ? this.lastReturnValue : (null != this.userDefinedLocation ? this.lastReturnValue = this.userDefinedLocation : (this.lastReturnValue = [c[0] + this.x * d[0] + this.offsets[0], c[1] + this.y * d[1] + this.offsets[1]], this.lastReturnValue = b.adjustForParentOffsetAndScroll(this.lastReturnValue, e.canvas)), this.timestamp = f, this.lastReturnValue)
        },
        this.getCurrentLocation = function (a) {
            return null == this.lastReturnValue || null != a.timestamp && this.timestamp != a.timestamp ? this.compute(a) : this.lastReturnValue
        }
    },
    jsPlumbUtil.extend(jsPlumb.Anchor, jsPlumbUtil.EventGenerator, {
        equals: function (a) {
            if (!a) return !1;
            var b = a.getOrientation(),
            c = this.getOrientation();
            return this.x == a.x && this.y == a.y && this.offsets[0] == a.offsets[0] && this.offsets[1] == a.offsets[1] && c[0] == b[0] && c[1] == b[1]
        },
        getUserDefinedLocation: function () {
            return this.userDefinedLocation
        },
        setUserDefinedLocation: function (a) {
            this.userDefinedLocation = a
        },
        clearUserDefinedLocation: function () {
            this.userDefinedLocation = null
        },
        getOrientation: function () {
            return this.orientation
        },
        getCssClass: function () {
            return this.cssClass
        }
    }),
    jsPlumb.FloatingAnchor = function (a) {
        jsPlumb.Anchor.apply(this, arguments);
        var b = a.reference,
        c = jsPlumb.CurrentLibrary,
        d = a.jsPlumbInstance,
        e = a.referenceCanvas,
        f = c.getSize(c.getElementObject(e)),
        g = 0,
        h = 0,
        i = null,
        j = null;
        this.orientation = null,
        this.x = 0,
        this.y = 0,
        this.isFloating = !0,
        this.compute = function (a) {
            var b = a.xy,
            c = a.element,
            e = [b[0] + f[0] / 2, b[1] + f[1] / 2];
            return e = d.adjustForParentOffsetAndScroll(e, c.canvas),
            j = e,
            e
        },
        this.getOrientation = function (a) {
            if (i) return i;
            var c = b.getOrientation(a);
            return [-1 * Math.abs(c[0]) * g, -1 * Math.abs(c[1]) * h]
        },
        this.over = function (a, b) {
            i = a.getOrientation(b)
        },
        this.out = function () {
            i = null
        },
        this.getCurrentLocation = function (a) {
            return null == j ? this.compute(a) : j
        }
    },
    jsPlumbUtil.extend(jsPlumb.FloatingAnchor, jsPlumb.Anchor);
    var a = function (a, b, c) {
        return a.constructor == jsPlumb.Anchor ? a : b.makeAnchor(a, c, b)
    };
    jsPlumb.DynamicAnchor = function (b) {
        jsPlumb.Anchor.apply(this, arguments),
        this.isSelective = !0,
        this.isDynamic = !0,
        this.anchors = [],
        this.elementId = b.elementId,
        this.jsPlumbInstance = b.jsPlumbInstance;
        for (var c = 0; c < b.anchors.length; c++) this.anchors[c] = a(b.anchors[c], this.jsPlumbInstance, this.elementId);
        this.addAnchor = function (b) {
            this.anchors.push(a(b, this.jsPlumbInstance, this.elementId))
        },
        this.getAnchors = function () {
            return this.anchors
        },
        this.locked = !1;
        var d = this.anchors.length > 0 ? this.anchors[0] : null,
        e = (this.anchors.length > 0 ? 0 : -1, d),
        f = this,
        g = function (a, b, c, d, e) {
            var f = d[0] + a.x * e[0],
            g = d[1] + a.y * e[1],
            h = d[0] + e[0] / 2,
            i = d[1] + e[1] / 2;
            return Math.sqrt(Math.pow(b - f, 2) + Math.pow(c - g, 2)) + Math.sqrt(Math.pow(h - f, 2) + Math.pow(i - g, 2))
        },
        h = b.selector ||
        function (a, b, c, d, e) {
            for (var f = c[0] + d[0] / 2, h = c[1] + d[1] / 2, i = -1, j = 1 / 0, k = 0; k < e.length; k++) {
                var l = g(e[k], f, h, a, b);
                j > l && (i = k + 0, j = l)
            }
            return e[i]
        };
        this.compute = function (a) {
            var b = a.xy,
            c = a.wh,
            g = a.timestamp,
            i = a.txy,
            j = a.twh;
            a.clearUserDefinedLocation && (userDefinedLocation = null),
            this.timestamp = g;
            var k = f.getUserDefinedLocation();
            return null != k ? k : this.locked || null == i || null == j ? d.compute(a) : (a.timestamp = null, d = h(b, c, i, j, this.anchors), this.x = d.x, this.y = d.y, d != e && this.fire("anchorChanged", d), e = d, d.compute(a))
        },
        this.getCurrentLocation = function (a) {
            return this.getUserDefinedLocation() || (null != d ? d.getCurrentLocation(a) : null)
        },
        this.getOrientation = function (a) {
            return null != d ? d.getOrientation(a) : [0, 0]
        },
        this.over = function (a, b) {
            null != d && d.over(a, b)
        },
        this.out = function () {
            null != d && d.out()
        },
        this.getCssClass = function () {
            return d && d.getCssClass() || ""
        }
    },
    jsPlumbUtil.extend(jsPlumb.DynamicAnchor, jsPlumb.Anchor);
    var b = function (a, b, c, d, e, f) {
        jsPlumb.Anchors[e] = function (g) {
            var h = g.jsPlumbInstance.makeAnchor([a, b, c, d, 0, 0], g.elementId, g.jsPlumbInstance);
            return h.type = e,
            f && f(h, g),
            h
        }
    };
    b(.5, 0, 0, -1, "TopCenter"),
    b(.5, 1, 0, 1, "BottomCenter"),
    b(0, .5, -1, 0, "LeftMiddle"),
    b(1, .5, 1, 0, "RightMiddle"),
    b(.5, 0, 0, -1, "Top"),
    b(.5, 1, 0, 1, "Bottom"),
    b(0, .5, -1, 0, "Left"),
    b(1, .5, 1, 0, "Right"),
    b(.5, .5, 0, 0, "Center"),
    b(1, 0, 0, -1, "TopRight"),
    b(1, 1, 0, 1, "BottomRight"),
    b(0, 0, 0, -1, "TopLeft"),
    b(0, 1, 0, 1, "BottomLeft"),
    jsPlumb.Defaults.DynamicAnchors = function (a) {
        return a.jsPlumbInstance.makeAnchors(["TopCenter", "RightMiddle", "BottomCenter", "LeftMiddle"], a.elementId, a.jsPlumbInstance)
    },
    jsPlumb.Anchors.AutoDefault = function (a) {
        var b = a.jsPlumbInstance.makeDynamicAnchor(jsPlumb.Defaults.DynamicAnchors(a));
        return b.type = "AutoDefault",
        b
    };
    var c = function (a, b) {
        jsPlumb.Anchors[a] = function (c) {
            var d = c.jsPlumbInstance.makeAnchor(["Continuous", {
                faces: b
            }], c.elementId, c.jsPlumbInstance);
            return d.type = a,
            d
        }
    };
    jsPlumb.Anchors.Continuous = function (a) {
        return a.jsPlumbInstance.continuousAnchorFactory.get(a)
    },
    c("ContinuousLeft", ["left"]),
    c("ContinuousTop", ["top"]),
    c("ContinuousBottom", ["bottom"]),
    c("ContinuousRight", ["right"]),
    jsPlumb.Anchors.Assign = b(0, 0, 0, 0, "Assign",
    function (a, b) {
        var c = b.position || "Fixed";
        a.positionFinder = c.constructor == String ? b.jsPlumbInstance.AnchorPositionFinders[c] : c,
        a.constructorParams = b
    }),
    jsPlumb.AnchorPositionFinders = {
        Fixed: function (a, b, c) {
            return [(a.left - b.left) / c[0], (a.top - b.top) / c[1]]
        },
        Grid: function (a, b, c, d) {
            var e = a.left - b.left,
            f = a.top - b.top,
            g = c[0] / d.grid[0],
            h = c[1] / d.grid[1],
            i = Math.floor(e / g),
            j = Math.floor(f / h);
            return [(i * g + g / 2) / c[0], (j * h + h / 2) / c[1]]
        }
    },
    jsPlumb.Anchors.Perimeter = function (a) {
        a = a || {};
        var b = a.anchorCount || 60,
        c = a.shape;
        if (!c) throw new Error("no shape supplied to Perimeter Anchor type");
        var d = function () {
            for (var a = .5,
            c = 2 * Math.PI / b,
            d = 0,
            e = [], f = 0; b > f; f++) {
                var g = a + a * Math.sin(d),
                h = a + a * Math.cos(d);
                e.push([g, h, 0, 0]),
                d += c
            }
            return e
        },
        e = function (a) {
            for (var c = b / a.length,
            d = [], e = function (a, e, f, g, h) {
                c = b * h;
                for (var i = (f - a) / c, j = (g - e) / c, k = 0; c > k; k++) d.push([a + i * k, e + j * k, 0, 0])
            },
            f = 0; f < a.length; f++) e.apply(null, a[f]);
            return d
        },
        f = function (a) {
            for (var b = [], c = 0; c < a.length; c++) b.push([a[c][0], a[c][1], a[c][2], a[c][3], 1 / a.length]);
            return e(b)
        },
        g = function () {
            return f([[0, 0, 1, 0], [1, 0, 1, 1], [1, 1, 0, 1], [0, 1, 0, 0]])
        },
        h = {
            Circle: d,
            Ellipse: d,
            Diamond: function () {
                return f([[.5, 0, 1, .5], [1, .5, .5, 1], [.5, 1, 0, .5], [0, .5, .5, 0]])
            },
            Rectangle: g,
            Square: g,
            Triangle: function () {
                return f([[.5, 0, 1, 1], [1, 1, 0, 1], [0, 1, .5, 0]])
            },
            Path: function (a) {
                for (var b = a.points,
                c = [], d = 0, f = 0; f < b.length - 1; f++) {
                    var g = Math.sqrt(Math.pow(b[f][2] - b[f][0]) + Math.pow(b[f][3] - b[f][1]));
                    d += g,
                    c.push([b[f][0], b[f][1], b[f + 1][0], b[f + 1][1], g])
                }
                for (var h = 0; h < c.length; h++) c[h][4] = c[h][4] / d;
                return e(c)
            }
        },
        i = function (a, b) {
            for (var c = [], d = b / 180 * Math.PI, e = 0; e < a.length; e++) {
                var f = a[e][0] - .5,
                g = a[e][1] - .5;
                c.push([.5 + (f * Math.cos(d) - g * Math.sin(d)), .5 + (f * Math.sin(d) + g * Math.cos(d)), a[e][2], a[e][3]])
            }
            return c
        };
        if (!h[c]) throw new Error("Shape [" + c + "] is unknown by Perimeter Anchor type");
        var j = h[c](a);
        a.rotation && (j = i(j, a.rotation));
        var k = a.jsPlumbInstance.makeDynamicAnchor(j);
        return k.type = "Perimeter",
        k
    }
}(),
function () {
    jsPlumb.DOMElementComponent = jsPlumbUtil.extend(jsPlumb.jsPlumbUIComponent,
    function () {
        this.mousemove = this.dblclick = this.click = this.mousedown = this.mouseup = function () { }
    }),
    jsPlumb.Segments = {
        AbstractSegment: function (a) {
            this.params = a,
            this.findClosestPointOnPath = function () {
                return {
                    d: 1 / 0,
                    x: null,
                    y: null,
                    l: null
                }
            },
            this.getBounds = function () {
                return {
                    minX: Math.min(a.x1, a.x2),
                    minY: Math.min(a.y1, a.y2),
                    maxX: Math.max(a.x1, a.x2),
                    maxY: Math.max(a.y1, a.y2)
                }
            }
        },
        Straight: function (a) {
            var b, c, d, e, f, g, h, i = (jsPlumb.Segments.AbstractSegment.apply(this, arguments),
            function () {
                b = Math.sqrt(Math.pow(f - e, 2) + Math.pow(h - g, 2)),
                c = jsPlumbUtil.gradient({
                    x: e,
                    y: g
                },
                {
                    x: f,
                    y: h
                }),
                d = -1 / c
            });
            this.type = "Straight",
            this.getLength = function () {
                return b
            },
            this.getGradient = function () {
                return c
            },
            this.getCoordinates = function () {
                return {
                    x1: e,
                    y1: g,
                    x2: f,
                    y2: h
                }
            },
            this.setCoordinates = function (a) {
                e = a.x1,
                g = a.y1,
                f = a.x2,
                h = a.y2,
                i()
            },
            this.setCoordinates({
                x1: a.x1,
                y1: a.y1,
                x2: a.x2,
                y2: a.y2
            }),
            this.getBounds = function () {
                return {
                    minX: Math.min(e, f),
                    minY: Math.min(g, h),
                    maxX: Math.max(e, f),
                    maxY: Math.max(g, h)
                }
            },
            this.pointOnPath = function (a, c) {
                if (0 !== a || c) {
                    if (1 != a || c) {
                        var d = c ? a > 0 ? a : b + a : a * b;
                        return jsPlumbUtil.pointOnLine({
                            x: e,
                            y: g
                        },
                        {
                            x: f,
                            y: h
                        },
                        d)
                    }
                    return {
                        x: f,
                        y: h
                    }
                }
                return {
                    x: e,
                    y: g
                }
            },
            this.gradientAtPoint = function () {
                return c
            },
            this.pointAlongPathFrom = function (a, b, c) {
                var d = this.pointOnPath(a, c),
                i = 0 >= b ? {
                    x: e,
                    y: g
                } : {
                    x: f,
                    y: h
                };
                return 0 >= b && Math.abs(b) > 1 && (b *= -1),
                jsPlumbUtil.pointOnLine(d, i, b)
            },
            this.findClosestPointOnPath = function (a, f) {
                if (0 === c) return {
                    x: a,
                    y: g,
                    d: Math.abs(f - g)
                };
                if (1 / 0 == c || c == -1 / 0) return {
                    x: e,
                    y: f,
                    d: Math.abs(a - 1)
                };
                var h = g - c * e,
                i = f - d * a,
                j = (i - h) / (c - d),
                k = c * j + h,
                l = jsPlumbUtil.lineLength([a, f], [j, k]),
                m = jsPlumbUtil.lineLength([j, k], [e, g]);
                return {
                    d: l,
                    x: j,
                    y: k,
                    l: m / b
                }
            }
        },
        Arc: function (a) {
            var b = (jsPlumb.Segments.AbstractSegment.apply(this, arguments),
            function (b, c) {
                return jsPlumbUtil.theta([a.cx, a.cy], [b, c])
            }),
            c = function (a, b) {
                if (a.anticlockwise) {
                    var c = a.startAngle < a.endAngle ? a.startAngle + d : a.startAngle,
                    e = Math.abs(c - a.endAngle);
                    return c - e * b
                }
                var f = a.endAngle < a.startAngle ? a.endAngle + d : a.endAngle,
                g = Math.abs(f - a.startAngle);
                return a.startAngle + g * b
            },
            d = 2 * Math.PI;
            this.radius = a.r,
            this.anticlockwise = a.ac,
            this.type = "Arc",
            a.startAngle && a.endAngle ? (this.startAngle = a.startAngle, this.endAngle = a.endAngle, this.x1 = a.cx + this.radius * Math.cos(a.startAngle), this.y1 = a.cy + this.radius * Math.sin(a.startAngle), this.x2 = a.cx + this.radius * Math.cos(a.endAngle), this.y2 = a.cy + this.radius * Math.sin(a.endAngle)) : (this.startAngle = b(a.x1, a.y1), this.endAngle = b(a.x2, a.y2), this.x1 = a.x1, this.y1 = a.y1, this.x2 = a.x2, this.y2 = a.y2),
            this.endAngle < 0 && (this.endAngle += d),
            this.startAngle < 0 && (this.startAngle += d),
            this.segment = jsPlumbUtil.segment([this.x1, this.y1], [this.x2, this.y2]);
            var e = this.endAngle < this.startAngle ? this.endAngle + d : this.endAngle;
            this.sweep = Math.abs(e - this.startAngle),
            this.anticlockwise && (this.sweep = d - this.sweep);
            var f = 2 * Math.PI * this.radius,
            g = this.sweep / d,
            h = f * g;
            this.getLength = function () {
                return h
            },
            this.getBounds = function () {
                return {
                    minX: a.cx - a.r,
                    maxX: a.cx + a.r,
                    minY: a.cy - a.r,
                    maxY: a.cy + a.r
                }
            };
            var i = 1e-10,
            j = function (a) {
                var b = Math.floor(a),
                c = Math.ceil(a);
                return i > a - b ? b : i > c - a ? c : a
            };
            this.pointOnPath = function (b, d) {
                if (0 === b) return {
                    x: this.x1,
                    y: this.y1,
                    theta: this.startAngle
                };
                if (1 == b) return {
                    x: this.x2,
                    y: this.y2,
                    theta: this.endAngle
                };
                d && (b /= h);
                var e = c(this, b),
                f = a.cx + a.r * Math.cos(e),
                g = a.cy + a.r * Math.sin(e);
                return {
                    x: j(f),
                    y: j(g),
                    theta: e
                }
            },
            this.gradientAtPoint = function (b, c) {
                var d = this.pointOnPath(b, c),
                e = jsPlumbUtil.normal([a.cx, a.cy], [d.x, d.y]);
                return this.anticlockwise || 1 / 0 != e && e != -1 / 0 || (e *= -1),
                e
            },
            this.pointAlongPathFrom = function (b, c, d) {
                var e = this.pointOnPath(b, d),
                g = 2 * (c / f) * Math.PI,
                h = this.anticlockwise ? -1 : 1,
                i = e.theta + h * g,
                j = a.cx + this.radius * Math.cos(i),
                k = a.cy + this.radius * Math.sin(i);
                return {
                    x: j,
                    y: k
                }
            }
        },
        Bezier: function (a) {
            var b = (jsPlumb.Segments.AbstractSegment.apply(this, arguments), [{
                x: a.x1,
                y: a.y1
            },
            {
                x: a.cp1x,
                y: a.cp1y
            },
            {
                x: a.cp2x,
                y: a.cp2y
            },
            {
                x: a.x2,
                y: a.y2
            }]),
            c = {
                minX: Math.min(a.x1, a.x2, a.cp1x, a.cp2x),
                minY: Math.min(a.y1, a.y2, a.cp1y, a.cp2y),
                maxX: Math.max(a.x1, a.x2, a.cp1x, a.cp2x),
                maxY: Math.max(a.y1, a.y2, a.cp1y, a.cp2y)
            };
            this.type = "Bezier";
            var d = function (a, b, c) {
                return c && (b = jsBezier.locationAlongCurveFrom(a, b > 0 ? 0 : 1, b)),
                b
            };
            this.pointOnPath = function (a, c) {
                return a = d(b, a, c),
                jsBezier.pointOnCurve(b, a)
            },
            this.gradientAtPoint = function (a, c) {
                return a = d(b, a, c),
                jsBezier.gradientAtPoint(b, a)
            },
            this.pointAlongPathFrom = function (a, c, e) {
                return a = d(b, a, e),
                jsBezier.pointAlongCurveFrom(b, a, c)
            },
            this.getLength = function () {
                return jsBezier.getLength(b)
            },
            this.getBounds = function () {
                return c
            }
        }
    };
    var a = function () {
        this.resetBounds = function () {
            this.bounds = {
                minX: 1 / 0,
                minY: 1 / 0,
                maxX: -1 / 0,
                maxY: -1 / 0
            }
        },
        this.resetBounds()
    };
    jsPlumb.Connectors.AbstractConnector = function (b) {
        a.apply(this, arguments);
        var c = [],
        d = 0,
        e = [],
        f = [],
        g = b.stub || 0,
        h = jsPlumbUtil.isArray(g) ? g[0] : g,
        i = jsPlumbUtil.isArray(g) ? g[1] : g,
        j = b.gap || 0,
        k = jsPlumbUtil.isArray(j) ? j[0] : j,
        l = jsPlumbUtil.isArray(j) ? j[1] : j,
        m = null,
        n = !1,
        o = null;
        this.isEditable = function () {
            return !1
        },
        this.setEdited = function (a) {
            n = a
        },
        this.getPath = function () { },
        this.setPath = function () { },
        this.findSegmentForPoint = function (a, b) {
            for (var d = {
                d: 1 / 0,
                s: null,
                x: null,
                y: null,
                l: null
            },
            e = 0; e < c.length; e++) {
                var f = c[e].findClosestPointOnPath(a, b);
                f.d < d.d && (d.d = f.d, d.l = f.l, d.x = f.x, d.y = f.y, d.s = c[e])
            }
            return d
        };
        var p = function () {
            for (var a = 0,
            b = 0; b < c.length; b++) {
                var g = c[b].getLength();
                f[b] = g / d,
                e[b] = [a, a += g / d]
            }
        },
        q = function (a, b) {
            b && (a = a > 0 ? a / d : (d + a) / d);
            for (var g = e.length - 1,
            h = 1,
            i = 0; i < e.length; i++) if (e[i][1] >= a) {
                g = i,
                h = 1 == a ? 1 : 0 === a ? 0 : (a - e[i][0]) / f[i];
                break
            }
            return {
                segment: c[g],
                proportion: h,
                index: g
            }
        },
        r = function (a, b, e) {
            if (e.x1 != e.x2 || e.y1 != e.y2) {
                var f = new jsPlumb.Segments[b](e);
                c.push(f),
                d += f.getLength(),
                a.updateBounds(f)
            }
        },
        s = function () {
            d = 0,
            c.splice(0, c.length),
            e.splice(0, e.length),
            f.splice(0, f.length)
        };
        this.setSegments = function (a) {
            m = [],
            d = 0;
            for (var b = 0; b < a.length; b++) m.push(a[b]),
            d += a[b].getLength()
        };
        var t = function (a) {
            this.lineWidth = a.lineWidth;
            var b = jsPlumbUtil.segment(a.sourcePos, a.targetPos),
            c = a.targetPos[0] < a.sourcePos[0],
            d = a.targetPos[1] < a.sourcePos[1],
            e = a.lineWidth || 1,
            f = a.sourceEndpoint.anchor.orientation || a.sourceEndpoint.anchor.getOrientation(a.sourceEndpoint),
            g = a.targetEndpoint.anchor.orientation || a.targetEndpoint.anchor.getOrientation(a.targetEndpoint),
            j = c ? a.targetPos[0] : a.sourcePos[0],
            m = d ? a.targetPos[1] : a.sourcePos[1],
            n = Math.abs(a.targetPos[0] - a.sourcePos[0]),
            o = Math.abs(a.targetPos[1] - a.sourcePos[1]);
            if (0 === f[0] && 0 === f[1] || 0 === g[0] && 0 === g[1]) {
                var p = n > o ? 0 : 1,
                q = [1, 0][p];
                f = [],
                g = [],
                f[p] = a.sourcePos[p] > a.targetPos[p] ? -1 : 1,
                g[p] = a.sourcePos[p] > a.targetPos[p] ? 1 : -1,
                f[q] = 0,
                g[q] = 0
            }
            var r = c ? n + k * f[0] : k * f[0],
            s = d ? o + k * f[1] : k * f[1],
            t = c ? l * g[0] : n + l * g[0],
            u = d ? l * g[1] : o + l * g[1],
            v = f[0] * g[0] + f[1] * g[1],
            w = {
                sx: r,
                sy: s,
                tx: t,
                ty: u,
                lw: e,
                xSpan: Math.abs(t - r),
                ySpan: Math.abs(u - s),
                mx: (r + t) / 2,
                my: (s + u) / 2,
                so: f,
                to: g,
                x: j,
                y: m,
                w: n,
                h: o,
                segment: b,
                startStubX: r + f[0] * h,
                startStubY: s + f[1] * h,
                endStubX: t + g[0] * i,
                endStubY: u + g[1] * i,
                isXGreaterThanStubTimes2: Math.abs(r - t) > h + i,
                isYGreaterThanStubTimes2: Math.abs(s - u) > h + i,
                opposite: -1 == v,
                perpendicular: 0 === v,
                orthogonal: 1 == v,
                sourceAxis: 0 === f[0] ? "y" : "x",
                points: [j, m, n, o, r, s, t, u]
            };
            return w.anchorOrientation = w.opposite ? "opposite" : w.orthogonal ? "orthogonal" : "perpendicular",
            w
        };
        return this.getSegments = function () {
            return c
        },
        this.updateBounds = function (a) {
            var b = a.getBounds();
            this.bounds.minX = Math.min(this.bounds.minX, b.minX),
            this.bounds.maxX = Math.max(this.bounds.maxX, b.maxX),
            this.bounds.minY = Math.min(this.bounds.minY, b.minY),
            this.bounds.maxY = Math.max(this.bounds.maxY, b.maxY)
        },
        this.pointOnPath = function (a, b) {
            var c = q(a, b);
            return c.segment && c.segment.pointOnPath(c.proportion, b) || [0, 0]
        },
        this.gradientAtPoint = function (a) {
            var b = q(a, absolute);
            return b.segment && b.segment.gradientAtPoint(b.proportion, absolute) || 0
        },
        this.pointAlongPathFrom = function (a, b, c) {
            var d = q(a, c);
            return d.segment && d.segment.pointAlongPathFrom(d.proportion, b, !1) || [0, 0]
        },
        this.compute = function (a) {
            n || (o = t(a)),
            s(),
            this._compute(o, a),
            this.x = o.points[0],
            this.y = o.points[1],
            this.w = o.points[2],
            this.h = o.points[3],
            this.segment = o.segment,
            p()
        },
        {
            addSegment: r,
            prepareCompute: t,
            sourceStub: h,
            targetStub: i,
            maxStub: Math.max(h, i),
            sourceGap: k,
            targetGap: l,
            maxGap: Math.max(k, l)
        }
    },
    jsPlumbUtil.extend(jsPlumb.Connectors.AbstractConnector, a);
    var b = function () {
        this.type = "Straight";
        var a = jsPlumb.Connectors.AbstractConnector.apply(this, arguments);
        this._compute = function (b) {
            a.addSegment(this, "Straight", {
                x1: b.sx,
                y1: b.sy,
                x2: b.startStubX,
                y2: b.startStubY
            }),
            a.addSegment(this, "Straight", {
                x1: b.startStubX,
                y1: b.startStubY,
                x2: b.endStubX,
                y2: b.endStubY
            }),
            a.addSegment(this, "Straight", {
                x1: b.endStubX,
                y1: b.endStubY,
                x2: b.tx,
                y2: b.ty
            })
        }
    };
    jsPlumbUtil.extend(jsPlumb.Connectors.Straight, jsPlumb.Connectors.AbstractConnector),
    jsPlumb.registerConnectorType(b, "Straight");
    var c = function (a) {
        a = a || {};
        var b = jsPlumb.Connectors.AbstractConnector.apply(this, arguments),
        c = (a.stub || 50, a.curviness || 150),
        d = 10;
        this.type = "Bezier",
        this.getCurviness = function () {
            return c
        },
        this._findControlPoint = function (a, b, e, f, g) {
            var h = f.anchor.getOrientation(f),
            i = g.anchor.getOrientation(g),
            j = h[0] != i[0] || h[1] == i[1],
            k = [];
            return j ? (0 === i[0] ? k.push(e[0] < b[0] ? a[0] + d : a[0] - d) : k.push(a[0] + c * i[0]), 0 === i[1] ? k.push(e[1] < b[1] ? a[1] + d : a[1] - d) : k.push(a[1] + c * h[1])) : (0 === h[0] ? k.push(b[0] < e[0] ? a[0] + d : a[0] - d) : k.push(a[0] - c * h[0]), 0 === h[1] ? k.push(b[1] < e[1] ? a[1] + d : a[1] - d) : k.push(a[1] + c * i[1])),
            k
        },
        this._compute = function (a, c) {
            var d = c.sourcePos,
            e = c.targetPos,
            f = Math.abs(d[0] - e[0]),
            g = Math.abs(d[1] - e[1]),
            h = d[0] < e[0] ? f : 0,
            i = d[1] < e[1] ? g : 0,
            j = d[0] < e[0] ? 0 : f,
            k = d[1] < e[1] ? 0 : g,
            l = this._findControlPoint([h, i], d, e, c.sourceEndpoint, c.targetEndpoint),
            m = this._findControlPoint([j, k], e, d, c.targetEndpoint, c.sourceEndpoint);
            b.addSegment(this, "Bezier", {
                x1: h,
                y1: i,
                x2: j,
                y2: k,
                cp1x: l[0],
                cp1y: l[1],
                cp2x: m[0],
                cp2y: m[1]
            })
        }
    };
    jsPlumbUtil.extend(c, jsPlumb.Connectors.AbstractConnector),
    jsPlumb.registerConnectorType(c, "Bezier"),
    jsPlumb.Endpoints.AbstractEndpoint = function (b) {
        a.apply(this, arguments);
        var c = this.compute = function () {
            var a = this._compute.apply(this, arguments);
            return this.x = a[0],
            this.y = a[1],
            this.w = a[2],
            this.h = a[3],
            this.bounds.minX = this.x,
            this.bounds.minY = this.y,
            this.bounds.maxX = this.x + this.w,
            this.bounds.maxY = this.y + this.h,
            a
        };
        return {
            compute: c,
            cssClass: b.cssClass
        }
    },
    jsPlumbUtil.extend(jsPlumb.Endpoints.AbstractEndpoint, a),
    jsPlumb.Endpoints.Dot = function (a) {
        this.type = "Dot",
        jsPlumb.Endpoints.AbstractEndpoint.apply(this, arguments),
        a = a || {},
        this.radius = a.radius || 10,
        this.defaultOffset = .5 * this.radius,
        this.defaultInnerRadius = this.radius / 3,
        this._compute = function (a, b, c) {
            this.radius = c.radius || this.radius;
            var d = a[0] - this.radius,
            e = a[1] - this.radius,
            f = 2 * this.radius,
            g = 2 * this.radius;
            if (c.strokeStyle) {
                var h = c.lineWidth || 1;
                d -= h,
                e -= h,
                f += 2 * h,
                g += 2 * h
            }
            return [d, e, f, g, this.radius]
        }
    },
    jsPlumbUtil.extend(jsPlumb.Endpoints.Dot, jsPlumb.Endpoints.AbstractEndpoint),
    jsPlumb.Endpoints.Rectangle = function (a) {
        this.type = "Rectangle",
        jsPlumb.Endpoints.AbstractEndpoint.apply(this, arguments),
        a = a || {},
        this.width = a.width || 20,
        this.height = a.height || 20,
        this._compute = function (a, b, c) {
            var d = c.width || this.width,
            e = c.height || this.height,
            f = a[0] - d / 2,
            g = a[1] - e / 2;
            return [f, g, d, e]
        }
    },
    jsPlumbUtil.extend(jsPlumb.Endpoints.Rectangle, jsPlumb.Endpoints.AbstractEndpoint);
    var d = function () {
        jsPlumb.DOMElementComponent.apply(this, arguments),
        this._jsPlumb.displayElements = []
    };
    jsPlumbUtil.extend(d, jsPlumb.DOMElementComponent, {
        getDisplayElements: function () {
            return this._jsPlumb.displayElements
        },
        appendDisplayElement: function (a) {
            this._jsPlumb.displayElements.push(a)
        }
    }),
    jsPlumb.Endpoints.Image = function (a) {
        this.type = "Image",
        d.apply(this, arguments),
        jsPlumb.Endpoints.AbstractEndpoint.apply(this, arguments);
        var b = a.onload,
        c = a.src || a.url,
        e = a.parent,
        f = a.cssClass ? " " + a.cssClass : "";
        this._jsPlumb.img = new Image,
        this._jsPlumb.ready = !1,
        this._jsPlumb.initialized = !1,
        this._jsPlumb.deleted = !1,
        this._jsPlumb.widthToUse = a.width,
        this._jsPlumb.heightToUse = a.height,
        this._jsPlumb.endpoint = a.endpoint,
        this._jsPlumb.img.onload = function () {
            null != this._jsPlumb && (this._jsPlumb.ready = !0, this._jsPlumb.widthToUse = this._jsPlumb.widthToUse || this._jsPlumb.img.width, this._jsPlumb.heightToUse = this._jsPlumb.heightToUse || this._jsPlumb.img.height, b && b(this))
        }.bind(this),
        this._jsPlumb.endpoint.setImage = function (a, c) {
            var d = a.constructor == String ? a : a.src;
            b = c,
            this._jsPlumb.img.src = d,
            null != this.canvas && this.canvas.setAttribute("src", this._jsPlumb.img.src)
        }.bind(this),
        this._jsPlumb.endpoint.setImage(c, b),
        this._compute = function (a) {
            return this.anchorPoint = a,
            this._jsPlumb.ready ? [a[0] - this._jsPlumb.widthToUse / 2, a[1] - this._jsPlumb.heightToUse / 2, this._jsPlumb.widthToUse, this._jsPlumb.heightToUse] : [0, 0, 0, 0]
        },
        this.canvas = document.createElement("img"),
        this.canvas.style.margin = 0,
        this.canvas.style.padding = 0,
        this.canvas.style.outline = 0,
        this.canvas.style.position = "absolute",
        this.canvas.className = this._jsPlumb.instance.endpointClass + f,
        this._jsPlumb.widthToUse && this.canvas.setAttribute("width", this._jsPlumb.widthToUse),
        this._jsPlumb.heightToUse && this.canvas.setAttribute("height", this._jsPlumb.heightToUse),
        this._jsPlumb.instance.appendElement(this.canvas, e),
        this.attachListeners(this.canvas, this),
        this.actuallyPaint = function () {
            if (!this._jsPlumb.deleted) {
                this._jsPlumb.initialized || (this.canvas.setAttribute("src", this._jsPlumb.img.src), this.appendDisplayElement(this.canvas), this._jsPlumb.initialized = !0);
                var a = this.anchorPoint[0] - this._jsPlumb.widthToUse / 2,
                b = this.anchorPoint[1] - this._jsPlumb.heightToUse / 2;
                jsPlumbUtil.sizeElement(this.canvas, a, b, this._jsPlumb.widthToUse, this._jsPlumb.heightToUse)
            }
        },
        this.paint = function (a, b) {
            null != this._jsPlumb && (this._jsPlumb.ready ? this.actuallyPaint(a, b) : window.setTimeout(function () {
                this.paint(a, b)
            }.bind(this), 200))
        }
    },
    jsPlumbUtil.extend(jsPlumb.Endpoints.Image, [d, jsPlumb.Endpoints.AbstractEndpoint], {
        cleanup: function () {
            this._jsPlumb.deleted = !0,
            jsPlumbUtil.removeElement(this.canvas),
            this.canvas = null
        }
    }),
    jsPlumb.Endpoints.Blank = function (a) {
        jsPlumb.Endpoints.AbstractEndpoint.apply(this, arguments),
        this.type = "Blank",
        d.apply(this, arguments),
        this._compute = function (a) {
            return [a[0], a[1], 10, 0]
        },
        this.canvas = document.createElement("div"),
        this.canvas.style.display = "block",
        this.canvas.style.width = "1px",
        this.canvas.style.height = "1px",
        this.canvas.style.background = "transparent",
        this.canvas.style.position = "absolute",
        this.canvas.className = this._jsPlumb.endpointClass,
        jsPlumb.appendElement(this.canvas, a.parent),
        this.paint = function () {
            jsPlumbUtil.sizeElement(this.canvas, this.x, this.y, this.w, this.h)
        }
    },
    jsPlumbUtil.extend(jsPlumb.Endpoints.Blank, [jsPlumb.Endpoints.AbstractEndpoint, d], {
        cleanup: function () {
            this.canvas && this.canvas.parentNode.removeChild(this.canvas)
        }
    }),
    jsPlumb.Endpoints.Triangle = function (a) {
        this.type = "Triangle",
        jsPlumb.Endpoints.AbstractEndpoint.apply(this, arguments),
        a = a || {},
        a.width = a.width || 55,
        a.height = a.height || 55,
        this.width = a.width,
        this.height = a.height,
        this._compute = function (a, b, c) {
            var d = c.width || self.width,
            e = c.height || self.height,
            f = a[0] - d / 2,
            g = a[1] - e / 2;
            return [f, g, d, e]
        }
    };
    var e = jsPlumb.Overlays.AbstractOverlay = function (a) {
        this.visible = !0,
        this.isAppendedAtTopLevel = !0,
        this.component = a.component,
        this.loc = null == a.location ? .5 : a.location,
        this.endpointLoc = null == a.endpointLocation ? [.5, .5] : a.endpointLocation
    };
    e.prototype = {
        cleanup: function () {
            this.component = null,
            this.canvas = null,
            this.endpointLoc = null
        },
        setVisible: function (a) {
            this.visible = a,
            this.component.repaint()
        },
        isVisible: function () {
            return this.visible
        },
        hide: function () {
            this.setVisible(!1)
        },
        show: function () {
            this.setVisible(!0)
        },
        incrementLocation: function (a) {
            this.loc += a,
            this.component.repaint()
        },
        setLocation: function (a) {
            this.loc = a,
            this.component.repaint()
        },
        getLocation: function () {
            return this.loc
        }
    },
    jsPlumb.Overlays.Arrow = function (a) {
        this.type = "Arrow",
        e.apply(this, arguments),
        this.isAppendedAtTopLevel = !1,
        a = a || {};
        var b = jsPlumbUtil;
        this.length = a.length || 20,
        this.width = a.width || 20,
        this.id = a.id;
        var c = (a.direction || 1) < 0 ? -1 : 1,
        d = a.paintStyle || {
            lineWidth: 1
        },
        f = a.foldback || .623;
        this.computeMaxSize = function () {
            return 1.5 * self.width
        },
        this.draw = function (a, e) {
            var g, h, i, j, k;
            if (a.pointAlongPathFrom) {
                if (b.isString(this.loc) || this.loc > 1 || this.loc < 0) {
                    var l = parseInt(this.loc, 10);
                    g = a.pointAlongPathFrom(l, c * this.length / 2, !0),
                    h = a.pointOnPath(l, !0),
                    i = b.pointOnLine(g, h, this.length)
                } else if (1 == this.loc) {
                    if (g = a.pointOnPath(this.loc), h = a.pointAlongPathFrom(this.loc, -this.length), i = b.pointOnLine(g, h, this.length), -1 == c) {
                        var m = i;
                        i = g,
                        g = m
                    }
                } else if (0 === this.loc) {
                    if (i = a.pointOnPath(this.loc), h = a.pointAlongPathFrom(this.loc, this.length), g = b.pointOnLine(i, h, this.length), -1 == c) {
                        var n = i;
                        i = g,
                        g = n
                    }
                } else g = a.pointAlongPathFrom(this.loc, c * this.length / 2),
                h = a.pointOnPath(this.loc),
                i = b.pointOnLine(g, h, this.length);
                j = b.perpendicularLineTo(g, i, this.width),
                k = b.pointOnLine(g, i, f * this.length);
                var o = {
                    hxy: g,
                    tail: j,
                    cxy: k
                },
                p = d.strokeStyle || e.strokeStyle,
                q = d.fillStyle || e.strokeStyle,
                r = d.lineWidth || e.lineWidth,
                s = {
                    component: a,
                    d: o,
                    lineWidth: r,
                    strokeStyle: p,
                    fillStyle: q,
                    minX: Math.min(g.x, j[0].x, j[1].x),
                    maxX: Math.max(g.x, j[0].x, j[1].x),
                    minY: Math.min(g.y, j[0].y, j[1].y),
                    maxY: Math.max(g.y, j[0].y, j[1].y)
                };
                return s
            }
            return {
                component: a,
                minX: 0,
                maxX: 0,
                minY: 0,
                maxY: 0
            }
        }
    },
    jsPlumbUtil.extend(jsPlumb.Overlays.Arrow, e),
    jsPlumb.Overlays.PlainArrow = function (a) {
        a = a || {};
        var b = jsPlumb.extend(a, {
            foldback: 1
        });
        jsPlumb.Overlays.Arrow.call(this, b),
        this.type = "PlainArrow"
    },
    jsPlumbUtil.extend(jsPlumb.Overlays.PlainArrow, jsPlumb.Overlays.Arrow),
    jsPlumb.Overlays.Diamond = function (a) {
        a = a || {};
        var b = a.length || 40,
        c = jsPlumb.extend(a, {
            length: b / 2,
            foldback: 2
        });
        jsPlumb.Overlays.Arrow.call(this, c),
        this.type = "Diamond"
    },
    jsPlumbUtil.extend(jsPlumb.Overlays.Diamond, jsPlumb.Overlays.Arrow);
    var f = function (a) {
        return null == a._jsPlumb.cachedDimensions && (a._jsPlumb.cachedDimensions = a.getDimensions()),
        a._jsPlumb.cachedDimensions
    },
    g = function (a) {
        jsPlumb.DOMElementComponent.apply(this, arguments),
        e.apply(this, arguments);
        var b = jsPlumb.CurrentLibrary;
        this.id = a.id,
        this._jsPlumb.div = null,
        this._jsPlumb.initialised = !1,
        this._jsPlumb.component = a.component,
        this._jsPlumb.cachedDimensions = null,
        this._jsPlumb.create = a.create,
        this.getElement = function () {
            if (null == this._jsPlumb.div) {
                var c = this._jsPlumb.div = b.getDOMElement(this._jsPlumb.create(this._jsPlumb.component));
                c.style.position = "absolute";
                var d = a._jsPlumb.overlayClass + " " + (this.cssClass ? this.cssClass : a.cssClass ? a.cssClass : "");
                c.className = d,
                this._jsPlumb.instance.appendElement(c, this._jsPlumb.component.parent),
                this._jsPlumb.instance.getId(c),
                this.attachListeners(c, this),
                this.canvas = c
            }
            return this._jsPlumb.div
        },
        this.draw = function (a) {
            var b = f(this);
            if (null != b && 2 == b.length) {
                var c = {
                    x: 0,
                    y: 0
                };
                if (a.pointOnPath) {
                    var d = this.loc,
                    e = !1; (jsPlumbUtil.isString(this.loc) || this.loc < 0 || this.loc > 1) && (d = parseInt(this.loc, 10), e = !0),
                    c = a.pointOnPath(d, e)
                } else {
                    var g = this.loc.constructor == Array ? this.loc : this.endpointLoc;
                    c = {
                        x: g[0] * a.w,
                        y: g[1] * a.h
                    }
                }
                var h = c.x - b[0] / 2,
                i = c.y - b[1] / 2;
                return {
                    component: a,
                    d: {
                        minx: h,
                        miny: i,
                        td: b,
                        cxy: c
                    },
                    minX: h,
                    maxX: h + b[0],
                    minY: i,
                    maxY: i + b[1]
                }
            }
            return {
                minX: 0,
                maxX: 0,
                minY: 0,
                maxY: 0
            }
        }
    };
    jsPlumbUtil.extend(g, [jsPlumb.DOMElementComponent, e], {
        getDimensions: function () {
            return jsPlumb.CurrentLibrary.getSize(jsPlumb.CurrentLibrary.getElementObject(this.getElement()))
        },
        setVisible: function (a) {
            this._jsPlumb.div.style.display = a ? "block" : "none"
        },
        clearCachedDimensions: function () {
            this._jsPlumb.cachedDimensions = null
        },
        cleanup: function () {
            null != this._jsPlumb.div && jsPlumb.CurrentLibrary.removeElement(this._jsPlumb.div)
        },
        computeMaxSize: function () {
            var a = f(this);
            return Math.max(a[0], a[1])
        },
        reattachListeners: function (a) {
            this._jsPlumb.div && this.reattachListenersForElement(this._jsPlumb.div, this, a)
        },
        paint: function (a) {
            this._jsPlumb.initialised || (this.getElement(), a.component.appendDisplayElement(this._jsPlumb.div), this.attachListeners(this._jsPlumb.div, a.component), this._jsPlumb.initialised = !0),
            this._jsPlumb.div.style.left = a.component.x + a.d.minx + "px",
            this._jsPlumb.div.style.top = a.component.y + a.d.miny + "px"
        }
    }),
    jsPlumb.Overlays.Custom = function () {
        this.type = "Custom",
        g.apply(this, arguments)
    },
    jsPlumbUtil.extend(jsPlumb.Overlays.Custom, g),
    jsPlumb.Overlays.GuideLines = function () {
        var a = this;
        a.length = 50,
        a.lineWidth = 5,
        this.type = "GuideLines",
        e.apply(this, arguments),
        jsPlumb.jsPlumbUIComponent.apply(this, arguments),
        this.draw = function (b) {
            var c = b.pointAlongPathFrom(a.loc, a.length / 2),
            d = b.pointOnPath(a.loc),
            e = jsPlumbUtil.pointOnLine(c, d, a.length),
            f = jsPlumbUtil.perpendicularLineTo(c, e, 40),
            g = jsPlumbUtil.perpendicularLineTo(e, c, 20);
            return {
                connector: b,
                head: c,
                tail: e,
                headLine: g,
                tailLine: f,
                minX: Math.min(c.x, e.x, g[0].x, g[1].x),
                minY: Math.min(c.y, e.y, g[0].y, g[1].y),
                maxX: Math.max(c.x, e.x, g[0].x, g[1].x),
                maxY: Math.max(c.y, e.y, g[0].y, g[1].y)
            }
        }
    },
    jsPlumb.Overlays.Label = function (a) {
        this.labelStyle = a.labelStyle || jsPlumb.Defaults.LabelStyle,
        this.cssClass = null != this.labelStyle ? this.labelStyle.cssClass : null;
        var b = jsPlumb.extend({
            create: function () {
                return document.createElement("div")
            }
        },
        a);
        jsPlumb.Overlays.Custom.call(this, b),
        this.type = "Label",
        this.label = a.label || "",
        this.labelText = null
    },
    jsPlumbUtil.extend(jsPlumb.Overlays.Label, jsPlumb.Overlays.Custom, {
        cleanup: function () {
            this.div = null,
            this.label = null,
            this.labelText = null,
            this.cssClass = null,
            this.labelStyle = null
        },
        getLabel: function () {
            return this.label
        },
        setLabel: function (a) {
            this.label = a,
            this.labelText = null,
            this.clearCachedDimensions(),
            this.update(),
            this.component.repaint()
        },
        getDimensions: function () {
            return this.update(),
            g.prototype.getDimensions.apply(this, arguments)
        },
        update: function () {
            if ("function" == typeof this.label) {
                var a = this.label(this);
                this.getElement().innerHTML = a.replace(/\r\n/g, "<br/>")
            } else null == this.labelText && (this.labelText = this.label, this.getElement().innerHTML = this.labelText.replace(/\r\n/g, "<br/>"))
        }
    })
}(),
function () {
    var a = function (a) {
        this.type = "Flowchart",
        a = a || {},
        a.stub = null == a.stub ? 30 : a.stub;
        var b, c = jsPlumb.Connectors.AbstractConnector.apply(this, arguments),
        d = null == a.midpoint ? .5 : a.midpoint,
        e = [],
        f = (a.grid, a.alwaysRespectStubs),
        g = null,
        h = null,
        i = null,
        j = null != a.cornerRadius ? a.cornerRadius : 0,
        k = function (a) {
            return 0 > a ? -1 : 0 === a ? 0 : 1
        },
        l = function (a, b, c, d) {
            if (h != b || i != c) {
                var e = null == h ? d.sx : h,
                f = null == i ? d.sy : i,
                g = e == b ? "v" : "h",
                j = k(b - e),
                l = k(c - f);
                h = b,
                i = c,
                a.push([e, f, b, c, g, j, l])
            }
        },
        m = function (a) {
            return Math.sqrt(Math.pow(a[0] - a[2], 2) + Math.pow(a[1] - a[3], 2))
        },
        n = function (a) {
            var b = [];
            return b.push.apply(b, a),
            b
        },
        o = function (a, b, d) {
            for (var e, f, g = 0; g < b.length - 1; g++) {
                if (e = e || n(b[g]), f = n(b[g + 1]), j > 0 && e[4] != f[4]) {
                    var h = Math.min(j, m(e), m(f));
                    e[2] -= e[5] * h,
                    e[3] -= e[6] * h,
                    f[0] += f[5] * h,
                    f[1] += f[6] * h;
                    var i = e[6] == f[5] && 1 == f[5] || e[6] == f[5] && 0 === f[5] && e[5] != f[6] || e[6] == f[5] && -1 == f[5],
                    k = f[1] > e[3] ? 1 : -1,
                    l = f[0] > e[2] ? 1 : -1,
                    o = k == l,
                    p = o && i || !o && !i ? f[0] : e[2],
                    q = o && i || !o && !i ? e[3] : f[1];
                    c.addSegment(a, "Straight", {
                        x1: e[0],
                        y1: e[1],
                        x2: e[2],
                        y2: e[3]
                    }),
                    c.addSegment(a, "Arc", {
                        r: h,
                        x1: e[2],
                        y1: e[3],
                        x2: f[0],
                        y2: f[1],
                        cx: p,
                        cy: q,
                        ac: i
                    })
                } else {
                    var r = e[2] == e[0] ? 0 : e[2] > e[0] ? d.lw / 2 : -(d.lw / 2),
                    s = e[3] == e[1] ? 0 : e[3] > e[1] ? d.lw / 2 : -(d.lw / 2);
                    c.addSegment(a, "Straight", {
                        x1: e[0] - r,
                        y1: e[1] - s,
                        x2: e[2] + r,
                        y2: e[3] + s
                    })
                }
                e = f
            }
            c.addSegment(a, "Straight", {
                x1: f[0],
                y1: f[1],
                x2: f[2],
                y2: f[3]
            })
        };
        this.setSegments = function (a) {
            g = a
        },
        this.isEditable = function () {
            return !0
        },
        this.getOriginalSegments = function () {
            return g || e
        },
        this._compute = function (a, j) {
            if (j.clearEdits && (g = null), null != g) return o(this, g, a),
            void 0;
            e = [],
            h = null,
            i = null,
            b = null;
            var k = a.startStubX + (a.endStubX - a.startStubX) * d,
            m = a.startStubY + (a.endStubY - a.startStubY) * d,
            n = {
                x: [0, 1],
                y: [1, 0]
            },
            p = function () {
                return [a.startStubX, a.startStubY, a.endStubX, a.endStubY]
            },
            q = {
                perpendicular: p,
                orthogonal: p,
                opposite: function (b) {
                    var c = a,
                    d = "x" == b ? 0 : 1,
                    e = {
                        x: function () {
                            return 1 == c.so[d] && (c.startStubX > c.endStubX && c.tx > c.startStubX || c.sx > c.endStubX && c.tx > c.sx) || -1 == c.so[d] && (c.startStubX < c.endStubX && c.tx < c.startStubX || c.sx < c.endStubX && c.tx < c.sx)
                        },
                        y: function () {
                            return 1 == c.so[d] && (c.startStubY > c.endStubY && c.ty > c.startStubY || c.sy > c.endStubY && c.ty > c.sy) || -1 == c.so[d] && (c.startStubY < c.endStubY && c.ty < c.startStubY || c.sy < c.endStubY && c.ty < c.sy)
                        }
                    };
                    return !f && e[b]() ? {
                        x: [(a.sx + a.tx) / 2, a.startStubY, (a.sx + a.tx) / 2, a.endStubY],
                        y: [a.startStubX, (a.sy + a.ty) / 2, a.endStubX, (a.sy + a.ty) / 2]
                    }[b] : [a.startStubX, a.startStubY, a.endStubX, a.endStubY]
                }
            },
            r = {
                perpendicular: function (b) {
                    var c = a,
                    d = {
                        x: [[[1, 2, 3, 4], null, [2, 1, 4, 3]], null, [[4, 3, 2, 1], null, [3, 4, 1, 2]]],
                        y: [[[3, 2, 1, 4], null, [2, 3, 4, 1]], null, [[4, 1, 2, 3], null, [1, 4, 3, 2]]]
                    },
                    e = {
                        x: [[c.startStubX, c.endStubX], null, [c.endStubX, c.startStubX]],
                        y: [[c.startStubY, c.endStubY], null, [c.endStubY, c.startStubY]]
                    },
                    f = {
                        x: [[k, c.startStubY], [k, c.endStubY]],
                        y: [[c.startStubX, m], [c.endStubX, m]]
                    },
                    g = {
                        x: [[c.endStubX, c.startStubY]],
                        y: [[c.startStubX, c.endStubY]]
                    },
                    h = {
                        x: [[c.startStubX, c.endStubY], [c.endStubX, c.endStubY]],
                        y: [[c.endStubX, c.startStubY], [c.endStubX, c.endStubY]]
                    },
                    i = {
                        x: [[c.startStubX, m], [c.endStubX, m], [c.endStubX, c.endStubY]],
                        y: [[k, c.startStubY], [k, c.endStubY], [c.endStubX, c.endStubY]]
                    },
                    j = {
                        x: [c.startStubY, c.endStubY],
                        y: [c.startStubX, c.endStubX]
                    },
                    l = n[b][0],
                    o = n[b][1],
                    p = c.so[l] + 1,
                    q = c.to[o] + 1,
                    r = -1 == c.to[o] && j[b][1] < j[b][0] || 1 == c.to[o] && j[b][1] > j[b][0],
                    s = e[b][p][0],
                    t = e[b][p][1],
                    u = d[b][p][q];
                    return c.segment == u[3] || c.segment == u[2] && r ? f[b] : c.segment == u[2] && s > t ? g[b] : c.segment == u[2] && t >= s || c.segment == u[1] && !r ? i[b] : c.segment == u[0] || c.segment == u[1] && r ? h[b] : void 0
                },
                orthogonal: function (b, c, d, e, f) {
                    var g = a,
                    h = {
                        x: -1 == g.so[0] ? Math.min(c, e) : Math.max(c, e),
                        y: -1 == g.so[1] ? Math.min(c, e) : Math.max(c, e)
                    }[b];
                    return {
                        x: [[h, d], [h, f], [e, f]],
                        y: [[d, h], [f, h], [f, e]]
                    }[b]
                },
                opposite: function (b, d, e, f) {
                    var g = a,
                    h = {
                        x: "y",
                        y: "x"
                    }[b],
                    i = {
                        x: "height",
                        y: "width"
                    }[b],
                    l = g["is" + b.toUpperCase() + "GreaterThanStubTimes2"];
                    if (j.sourceEndpoint.elementId == j.targetEndpoint.elementId) {
                        var n = e + (1 - j.sourceEndpoint.anchor[h]) * j.sourceInfo[i] + c.maxStub;
                        return {
                            x: [[d, n], [f, n]],
                            y: [[n, d], [n, f]]
                        }[b]
                    }
                    return !l || 1 == g.so[t] && d > f || -1 == g.so[t] && f > d ? {
                        x: [[d, m], [f, m]],
                        y: [[k, d], [k, f]]
                    }[b] : 1 == g.so[t] && f > d || -1 == g.so[t] && d > f ? {
                        x: [[k, g.sy], [k, g.ty]],
                        y: [[g.sx, m], [g.tx, m]]
                    }[b] : void 0
                }
            },
            s = q[a.anchorOrientation](a.sourceAxis),
            t = "x" == a.sourceAxis ? 0 : 1,
            u = "x" == a.sourceAxis ? 1 : 0,
            v = s[t],
            w = s[u],
            x = s[t + 2],
            y = s[u + 2];
            l(e, s[0], s[1], a);
            var z = r[a.anchorOrientation](a.sourceAxis, v, w, x, y);
            if (z) for (var A = 0; A < z.length; A++) l(e, z[A][0], z[A][1], a);
            l(e, s[2], s[3], a),
            l(e, a.tx, a.ty, a),
            o(this, e, a)
        },
        this.getPath = function () {
            for (var a = null,
            b = null,
            c = [], d = g || e, f = 0; f < d.length; f++) {
                var h = d[f],
                i = h[4],
                j = "v" == i ? 3 : 2;
                null != a && b === i ? a[j] = h[j] : (h[0] != h[2] || h[1] != h[3]) && (c.push({
                    start: [h[0], h[1]],
                    end: [h[2], h[3]]
                }), a = h, b = h[4])
            }
            return c
        },
        this.setPath = function (a) {
            g = [];
            for (var b = 0; b < a.length; b++) {
                var c = a[b].start[0],
                d = a[b].start[1],
                e = a[b].end[0],
                f = a[b].end[1],
                h = c == e ? "v" : "h",
                i = k(e - c),
                j = k(f - d);
                g.push([c, d, e, f, h, i, j])
            }
        }
    };
    jsPlumbUtil.extend(a, jsPlumb.Connectors.AbstractConnector),
    jsPlumb.registerConnectorType(a, "Flowchart")
}(),
function () {
    var a = function (a, b, c, d) {
        return c >= a && b >= d ? 1 : c >= a && d >= b ? 2 : a >= c && d >= b ? 3 : 4
    },
    b = function (a, b, c, d, e, f, g, h, i) {
        return i >= h ? [a, b] : 1 === c ? d[3] <= 0 && e[3] >= 1 ? [a + (d[2] < .5 ? -1 * f : f), b] : d[2] >= 1 && e[2] <= 0 ? [a, b + (d[3] < .5 ? -1 * g : g)] : [a + -1 * f, b + -1 * g] : 2 === c ? d[3] >= 1 && e[3] <= 0 ? [a + (d[2] < .5 ? -1 * f : f), b] : d[2] >= 1 && e[2] <= 0 ? [a, b + (d[3] < .5 ? -1 * g : g)] : [a + 1 * f, b + -1 * g] : 3 === c ? d[3] >= 1 && e[3] <= 0 ? [a + (d[2] < .5 ? -1 * f : f), b] : d[2] <= 0 && e[2] >= 1 ? [a, b + (d[3] < .5 ? -1 * g : g)] : [a + -1 * f, b + -1 * g] : 4 === c ? d[3] <= 0 && e[3] >= 1 ? [a + (d[2] < .5 ? -1 * f : f), b] : d[2] <= 0 && e[2] >= 1 ? [a, b + (d[3] < .5 ? -1 * g : g)] : [a + 1 * f, b + -1 * g] : void 0
    },
    c = function (c) {
        c = c || {},
        this.type = "StateMachine";
        var d = jsPlumb.Connectors.AbstractConnector.apply(this, arguments),
        e = c.curviness || 10,
        f = c.margin || 5,
        g = c.proximityLimit || 80,
        h = c.orientation && "clockwise" === c.orientation,
        i = c.loopbackRadius || 25,
        j = c.showLoopback !== !1;
        this._compute = function (c, k) {
            var l = Math.abs(k.sourcePos[0] - k.targetPos[0]),
            m = Math.abs(k.sourcePos[1] - k.targetPos[1]);
            if (Math.min(k.sourcePos[0], k.targetPos[0]), Math.min(k.sourcePos[1], k.targetPos[1]), j && k.sourceEndpoint.elementId === k.targetEndpoint.elementId) {
                var n = k.sourcePos[0],
                o = (k.sourcePos[0], k.sourcePos[1] - f),
                p = (k.sourcePos[1] - f, n),
                q = o - i,
                r = 2 * i,
                s = 2 * i,
                t = p - i,
                u = q - i;
                c.points[0] = t,
                c.points[1] = u,
                c.points[2] = r,
                c.points[3] = s,
                d.addSegment(this, "Arc", {
                    x1: n - t + 4,
                    y1: o - u,
                    startAngle: 0,
                    endAngle: 2 * Math.PI,
                    r: i,
                    ac: !h,
                    x2: n - t - 4,
                    y2: o - u,
                    cx: p - t,
                    cy: q - u
                })
            } else {
                var v = k.sourcePos[0] < k.targetPos[0] ? 0 : l,
                w = k.sourcePos[1] < k.targetPos[1] ? 0 : m,
                x = k.sourcePos[0] < k.targetPos[0] ? l : 0,
                y = k.sourcePos[1] < k.targetPos[1] ? m : 0;
                0 === k.sourcePos[2] && (v -= f),
                1 === k.sourcePos[2] && (v += f),
                0 === k.sourcePos[3] && (w -= f),
                1 === k.sourcePos[3] && (w += f),
                0 === k.targetPos[2] && (x -= f),
                1 === k.targetPos[2] && (x += f),
                0 === k.targetPos[3] && (y -= f),
                1 === k.targetPos[3] && (y += f);
                var z = (v + x) / 2,
                A = (w + y) / 2,
                B = -1 * z / A,
                C = Math.atan(B),
                D = (1 / 0 == B || B == -1 / 0 ? 0 : Math.abs(e / 2 * Math.sin(C)), 1 / 0 == B || B == -1 / 0 ? 0 : Math.abs(e / 2 * Math.cos(C)), a(v, w, x, y)),
                E = Math.sqrt(Math.pow(x - v, 2) + Math.pow(y - w, 2)),
                F = b(z, A, D, k.sourcePos, k.targetPos, e, e, E, g);
                d.addSegment(this, "Bezier", {
                    x1: x,
                    y1: y,
                    x2: v,
                    y2: w,
                    cp1x: F[0],
                    cp1y: F[1],
                    cp2x: F[0],
                    cp2y: F[1]
                })
            }
        }
    };
    jsPlumb.registerConnectorType(c, "StateMachine")
}(),
function () {
    var a = function (a) {
        a = a || {};
        var b = this,
        c = jsPlumb.Connectors.AbstractConnector.apply(this, arguments),
        d = (a.stub || 50, a.curviness || 150),
        e = 10;
        this.type = "Bezier",
        this.getCurviness = function () {
            return d
        },
        this._findControlPoint = function (a, b, c, f, g) {
            var h = f.anchor.getOrientation(f),
            i = g.anchor.getOrientation(g),
            j = h[0] != i[0] || h[1] == i[1],
            k = [];
            return j ? (0 === i[0] ? k.push(c[0] < b[0] ? a[0] + e : a[0] - e) : k.push(a[0] + d * i[0]), 0 === i[1] ? k.push(c[1] < b[1] ? a[1] + e : a[1] - e) : k.push(a[1] + d * h[1])) : (0 === h[0] ? k.push(b[0] < c[0] ? a[0] + e : a[0] - e) : k.push(a[0] - d * h[0]), 0 === h[1] ? k.push(b[1] < c[1] ? a[1] + e : a[1] - e) : k.push(a[1] + d * i[1])),
            k
        },
        this._compute = function (a, d) {
            var e = d.sourcePos,
            f = d.targetPos,
            g = Math.abs(e[0] - f[0]),
            h = Math.abs(e[1] - f[1]),
            i = e[0] < f[0] ? g : 0,
            j = e[1] < f[1] ? h : 0,
            k = e[0] < f[0] ? 0 : g,
            l = e[1] < f[1] ? 0 : h,
            m = b._findControlPoint([i, j], e, f, d.sourceEndpoint, d.targetEndpoint),
            n = b._findControlPoint([k, l], f, e, d.targetEndpoint, d.sourceEndpoint);
            c.addSegment(this, "Bezier", {
                x1: i,
                y1: j,
                x2: k,
                y2: l,
                cp1x: m[0],
                cp1y: m[1],
                cp2x: n[0],
                cp2y: n[1]
            })
        }
    };
    jsPlumb.registerConnectorType(a, "Bezier")
}(),
function () {
    var a = null,
    b = function (a, b) {
        return jsPlumb.CurrentLibrary.hasClass(c(a), b)
    },
    c = function (a) {
        return jsPlumb.CurrentLibrary.getElementObject(a)
    },
    d = function (a) {
        return jsPlumb.CurrentLibrary.getOffset(c(a))
    },
    e = function (a) {
        return jsPlumb.CurrentLibrary.getPageXY(a)
    },
    f = function (a) {
        return jsPlumb.CurrentLibrary.getClientXY(a)
    },
    g = window.CanvasMouseAdapter = function () {
        var g = this;
        g.overlayPlacements = [],
        jsPlumb.jsPlumbUIComponent.apply(this, arguments),
        jsPlumbUtil.EventGenerator.apply(this, arguments),
        this._over = function (a) {
            var b = d(c(g.canvas)),
            f = e(a),
            h = f[0] - b.left,
            i = f[1] - b.top;
            if (h > 0 && i > 0 && h < g.canvas.width && i < g.canvas.height) {
                for (var j = 0; j < g.overlayPlacements.length; j++) {
                    var k = g.overlayPlacements[j];
                    if (k && k[0] <= h && k[1] >= h && k[2] <= i && k[3] >= i) return !0
                }
                var l = g.canvas.getContext("2d").getImageData(parseInt(h, 10), parseInt(i, 10), 1, 1);
                return 0 !== l.data[0] || 0 !== l.data[1] || 0 !== l.data[2] || 0 !== l.data[3]
            }
            return !1
        };
        var h = !1,
        i = !1,
        j = null,
        k = !1,
        l = function (a, c) {
            return null !== a && b(a, c)
        };
        this.mousemove = function (b) {
            var c = (e(b), f(b)),
            d = document.elementFromPoint(c[0], c[1]),
            i = l(d, "_jsPlumb_overlay"),
            j = null === a && (l(d, "_jsPlumb_endpoint") || l(d, "_jsPlumb_connector"));
            return !h && j && g._over(b) ? (h = !0, g.fire("mouseenter", g, b), !0) : (!h || g._over(b) && j || i || (h = !1, g.fire("mouseexit", g, b)), g.fire("mousemove", g, b), void 0)
        },
        this.click = function (a) {
            h && g._over(a) && !k && g.fire("click", g, a),
            k = !1
        },
        this.dblclick = function (a) {
            h && g._over(a) && !k && g.fire("dblclick", g, a),
            k = !1
        },
        this.mousedown = function (a) {
            g._over(a) && !i && (i = !0, j = d(c(g.canvas)), g.fire("mousedown", g, a))
        },
        this.mouseup = function (a) {
            i = !1,
            g.fire("mouseup", g, a)
        },
        this.contextmenu = function (a) {
            h && g._over(a) && !k && g.fire("contextmenu", g, a),
            k = !1
        }
    };
    jsPlumbUtil.extend(g, [jsPlumb.jsPlumbUIComponent, jsPlumbUtil.EventGenerator]);
    var h = function (a) {
        var b = document.createElement("canvas");
        return a._jsPlumb.instance.appendElement(b, a.parent),
        b.style.position = "absolute",
        a["class"] && (b.className = a["class"]),
        a._jsPlumb.instance.getId(b, a.uuid),
        a.tooltip && b.setAttribute("title", a.tooltip),
        b
    },
    i = window.CanvasComponent = function () {
        g.apply(this, arguments);
        var a = [];
        this.getDisplayElements = function () {
            return a
        },
        this.appendDisplayElement = function (b) {
            a.push(b)
        }
    };
    jsPlumbUtil.extend(i, g);
    var j = [null, [1, -1], [1, 1], [-1, 1], [-1, -1]],
    k = function (a, b, c) {
        if (b.gradient) {
            for (var d = c(), e = 0; e < b.gradient.stops.length; e++) d.addColorStop(b.gradient.stops[e][0], b.gradient.stops[e][1]);
            a.strokeStyle = d
        }
    },
    l = function (a, b, c, d, e) {
        ({
            Straight: function (a, b, c, d, e) {
                var f = a.params;
                if (b.save(), k(b, c,
                function () {
                    return b.createLinearGradient(f.x1, f.y1, f.x2, f.y2)
                }), b.beginPath(), b.translate(d, e), c.dashstyle && 2 === c.dashstyle.split(" ").length) {
                    var g = c.dashstyle.split(" ");
                    2 !== g.length && (g = [2, 2]);
                    for (var h = [g[0] * c.lineWidth, g[1] * c.lineWidth], i = (f.x2 - f.x1) / (f.y2 - f.y1), l = jsPlumbUtil.segment([f.x1, f.y1], [f.x2, f.y2]), m = j[l], n = Math.atan(i), o = Math.sqrt(Math.pow(f.x2 - f.x1, 2) + Math.pow(f.y2 - f.y1, 2)), p = Math.floor(o / (h[0] + h[1])), q = [f.x1, f.y1], r = 0; p > r; r++) {
                        b.moveTo(q[0], q[1]);
                        var s = q[0] + Math.abs(Math.sin(n) * h[0]) * m[0],
                        t = q[1] + Math.abs(Math.cos(n) * h[0]) * m[1],
                        u = q[0] + Math.abs(Math.sin(n) * (h[0] + h[1])) * m[0],
                        v = q[1] + Math.abs(Math.cos(n) * (h[0] + h[1])) * m[1];
                        b.lineTo(s, t),
                        q = [u, v]
                    }
                    b.moveTo(q[0], q[1]),
                    b.lineTo(f.x2, f.y2)
                } else b.moveTo(f.x1, f.y1),
                b.lineTo(f.x2, f.y2);
                b.stroke(),
                b.restore()
            },
            Bezier: function (a, b, c, d, e) {
                var f = a.params;
                b.save(),
                k(b, c,
                function () {
                    return b.createLinearGradient(f.x2 + d, f.y2 + e, f.x1 + d, f.y1 + e)
                }),
                b.beginPath(),
                b.translate(d, e),
                b.moveTo(f.x1, f.y1),
                b.bezierCurveTo(f.cp1x, f.cp1y, f.cp2x, f.cp2y, f.x2, f.y2),
                b.stroke(),
                b.restore()
            },
            Arc: function (a, b, c, d, e) {
                var f = a.params;
                b.save(),
                b.beginPath(),
                b.translate(d, e),
                b.arc(f.cx, f.cy, f.r, a.startAngle, a.endAngle, f.ac),
                b.stroke(),
                b.restore()
            }
        })[a.type](a, b, c, d, e)
    },
    m = jsPlumb.ConnectorRenderers.canvas = function (a) {
        i.apply(this, arguments);
        var b = function (a, b, c) {
            this.ctx.save(),
            jsPlumb.extend(this.ctx, a);
            for (var d = this.getSegments(), e = 0; e < d.length; e++) l(d[e], this.ctx, a, b, c);
            this.ctx.restore()
        }.bind(this),
        c = this._jsPlumb.instance.connectorClass + " " + (a.cssClass || "");
        this.canvas = h({
            "class": c,
            _jsPlumb: this._jsPlumb,
            parent: a.parent
        }),
        this.ctx = this.canvas.getContext("2d"),
        this.appendDisplayElement(this.canvas),
        this.paint = function (a, c, d) {
            if (null != a) {
                var e = [this.x, this.y],
                f = [this.w, this.h],
                g = 0,
                h = 0;
                if (null != d && (d.xmin < 0 && (e[0] += d.xmin, g = -d.xmin), d.ymin < 0 && (e[1] += d.ymin, h = -d.ymin), f[0] = d.xmax + (d.xmin < 0 ? -d.xmin : 0), f[1] = d.ymax + (d.ymin < 0 ? -d.ymin : 0)), this.translateX = g, this.translateY = h, jsPlumbUtil.sizeElement(this.canvas, e[0], e[1], f[0], f[1]), null != a.outlineColor) {
                    var i = a.outlineWidth || 1,
                    j = a.lineWidth + 2 * i,
                    k = {
                        strokeStyle: a.outlineColor,
                        lineWidth: j
                    };
                    b(k, g, h)
                }
                b(a, g, h)
            }
        }
    };
    jsPlumbUtil.extend(m, i);
    var n = function (a) {
        i.apply(this, arguments);
        var b = this._jsPlumb.instance.endpointClass + " " + (a.cssClass || ""),
        c = {
            "class": b,
            _jsPlumb: this._jsPlumb,
            parent: a.parent,
            tooltip: self.tooltip
        };
        this.canvas = h(c),
        this.ctx = this.canvas.getContext("2d"),
        this.appendDisplayElement(this.canvas),
        this.paint = function (a) {
            if (jsPlumbUtil.sizeElement(this.canvas, this.x, this.y, this.w, this.h), null != a.outlineColor) {
                var b = a.outlineWidth || 1,
                c = a.lineWidth + 2 * b; ({
                    strokeStyle: a.outlineColor,
                    lineWidth: c
                })
            }
            this._paint.apply(this, arguments)
        }
    };
    jsPlumbUtil.extend(n, i),
    jsPlumb.Endpoints.canvas.Dot = function (a) {
        jsPlumb.Endpoints.Dot.apply(this, arguments),
        n.apply(this, arguments);
        var b = this,
        c = function (a) {
            try {
                return parseInt(a, 10)
            } catch (b) {
                if ("%" == a.substring(a.length - 1)) return parseInt(a.substring(0, a - 1), 10)
            }
        },
        d = function (a) {
            var d = b.defaultOffset,
            e = b.defaultInnerRadius;
            return a.offset && (d = c(a.offset)),
            a.innerRadius && (e = c(a.innerRadius)),
            [d, e]
        };
        this._paint = function (c) {
            if (null != c) {
                var e = b.canvas.getContext("2d"),
                f = a.endpoint.anchor.getOrientation(a.endpoint);
                if (jsPlumb.extend(e, c), c.gradient) {
                    for (var g = d(c.gradient), h = 1 == f[1] ? -1 * g[0] : g[0], i = 1 == f[0] ? -1 * g[0] : g[0], j = e.createRadialGradient(b.radius, b.radius, b.radius, b.radius + i, b.radius + h, g[1]), k = 0; k < c.gradient.stops.length; k++) j.addColorStop(c.gradient.stops[k][0], c.gradient.stops[k][1]);
                    e.fillStyle = j
                }
                e.beginPath(),
                e.arc(b.radius, b.radius, b.radius, 0, 2 * Math.PI, !0),
                e.closePath(),
                (c.fillStyle || c.gradient) && e.fill(),
                c.strokeStyle && e.stroke()
            }
        }
    },
    jsPlumbUtil.extend(jsPlumb.Endpoints.canvas.Dot, [jsPlumb.Endpoints.Dot, n]),
    jsPlumb.Endpoints.canvas.Rectangle = function (a) {
        var b = this;
        jsPlumb.Endpoints.Rectangle.apply(this, arguments),
        n.apply(this, arguments),
        this._paint = function (c) {
            var d = b.canvas.getContext("2d"),
            e = a.endpoint.anchor.getOrientation(a.endpoint);
            if (jsPlumb.extend(d, c), c.gradient) {
                for (var f = 1 == e[1] ? b.h : 0 === e[1] ? b.h / 2 : 0, g = -1 == e[1] ? b.h : 0 === e[1] ? b.h / 2 : 0, h = 1 == e[0] ? b.w : 0 === e[0] ? b.w / 2 : 0, i = -1 == e[0] ? b.w : 0 === e[0] ? b.w / 2 : 0, j = d.createLinearGradient(h, f, i, g), k = 0; k < c.gradient.stops.length; k++) j.addColorStop(c.gradient.stops[k][0], c.gradient.stops[k][1]);
                d.fillStyle = j
            }
            d.beginPath(),
            d.rect(0, 0, b.w, b.h),
            d.closePath(),
            (c.fillStyle || c.gradient) && d.fill(),
            c.strokeStyle && d.stroke()
        }
    },
    jsPlumbUtil.extend(jsPlumb.Endpoints.canvas.Rectangle, [jsPlumb.Endpoints.Rectangle, n]),
    jsPlumb.Endpoints.canvas.Triangle = function (a) {
        var b = this;
        jsPlumb.Endpoints.Triangle.apply(this, arguments),
        n.apply(this, arguments),
        this._paint = function (c) {
            var d = b.canvas.getContext("2d"),
            e = 0,
            f = 0,
            g = 0,
            h = a.endpoint.anchor.getOrientation(a.endpoint);
            1 == h[0] && (e = b.width, f = b.height, g = 180),
            -1 == h[1] && (e = b.width, g = 90),
            1 == h[1] && (f = b.height, g = -90),
            d.fillStyle = c.fillStyle,
            d.translate(e, f),
            d.rotate(g * Math.PI / 180),
            d.beginPath(),
            d.moveTo(0, 0),
            d.lineTo(b.width / 2, b.height / 2),
            d.lineTo(0, b.height),
            d.closePath(),
            (c.fillStyle || c.gradient) && d.fill(),
            c.strokeStyle && d.stroke()
        }
    },
    jsPlumbUtil.extend(jsPlumb.Endpoints.canvas.Triangle, [jsPlumb.Endpoints.Triangle, n]),
    jsPlumb.Endpoints.canvas.Image = jsPlumb.Endpoints.Image,
    jsPlumb.Endpoints.canvas.Blank = jsPlumb.Endpoints.Blank,
    jsPlumb.Overlays.canvas.Label = jsPlumb.Overlays.Label,
    jsPlumb.Overlays.canvas.Custom = jsPlumb.Overlays.Custom;
    var o = function () {
        jsPlumb.jsPlumbUIComponent.apply(this, arguments)
    };
    jsPlumbUtil.extend(o, jsPlumb.jsPlumbUIComponent);
    var p = function (a, b) {
        a.apply(this, b),
        o.apply(this, b),
        this.paint = function (a) {
            var b = a.component.ctx,
            c = a.d;
            c && (b.save(), b.lineWidth = a.lineWidth, b.beginPath(), b.translate(a.component.translateX, a.component.translateY), b.moveTo(c.hxy.x, c.hxy.y), b.lineTo(c.tail[0].x, c.tail[0].y), b.lineTo(c.cxy.x, c.cxy.y), b.lineTo(c.tail[1].x, c.tail[1].y), b.lineTo(c.hxy.x, c.hxy.y), b.closePath(), a.strokeStyle && (b.strokeStyle = a.strokeStyle, b.stroke()), a.fillStyle && (b.fillStyle = a.fillStyle, b.fill()), b.restore())
        }
    };
    jsPlumb.Overlays.canvas.Arrow = function () {
        p.apply(this, [jsPlumb.Overlays.Arrow, arguments])
    },
    jsPlumbUtil.extend(jsPlumb.Overlays.canvas.Arrow, [jsPlumb.Overlays.Arrow, o]),
    jsPlumb.Overlays.canvas.PlainArrow = function () {
        p.apply(this, [jsPlumb.Overlays.PlainArrow, arguments])
    },
    jsPlumbUtil.extend(jsPlumb.Overlays.canvas.PlainArrow, [jsPlumb.Overlays.PlainArrow, o]),
    jsPlumb.Overlays.canvas.Diamond = function () {
        p.apply(this, [jsPlumb.Overlays.Diamond, arguments])
    },
    jsPlumbUtil.extend(jsPlumb.Overlays.canvas.Diamond, [jsPlumb.Overlays.Diamond, o])
}(),
function () {
    var a = {
        joinstyle: "stroke-linejoin",
        "stroke-linejoin": "stroke-linejoin",
        "stroke-dashoffset": "stroke-dashoffset",
        "stroke-linecap": "stroke-linecap"
    },
    b = "stroke-dasharray",
    c = "dashstyle",
    d = "linearGradient",
    e = "radialGradient",
    f = "fill",
    g = "stop",
    h = "stroke",
    i = "stroke-width",
    j = "style",
    k = "none",
    l = "jsplumb_gradient_",
    m = "lineWidth",
    n = {
        svg: "http://www.w3.org/2000/svg",
        xhtml: "http://www.w3.org/1999/xhtml"
    },
    o = function (a, b) {
        for (var c in b) a.setAttribute(c, "" + b[c])
    },
    p = function (a, b) {
        var c = document.createElementNS(n.svg, a);
        return b = b || {},
        b.version = "1.1",
        b.xmlns = n.xhtml,
        o(c, b),
        c
    },
    q = function (a) {
        return "position:absolute;left:" + a[0] + "px;top:" + a[1] + "px"
    },
    r = function (a) {
        for (var b = 0; b < a.childNodes.length; b++) (a.childNodes[b].tagName == d || a.childNodes[b].tagName == e) && a.removeChild(a.childNodes[b])
    },
    s = function (a, b, c, i, k) {
        var m = l + k._jsPlumb.instance.idstamp();
        r(a);
        var n;
        n = c.gradient.offset ? p(e, {
            id: m
        }) : p(d, {
            id: m,
            gradientUnits: "userSpaceOnUse"
        }),
        a.appendChild(n);
        for (var o = 0; o < c.gradient.stops.length; o++) {
            var q = 1 == k.segment || 2 == k.segment ? o : c.gradient.stops.length - 1 - o,
            s = jsPlumbUtil.convertStyle(c.gradient.stops[q][1], !0),
            t = p(g, {
                offset: Math.floor(100 * c.gradient.stops[o][0]) + "%",
                "stop-color": s
            });
            n.appendChild(t)
        }
        var u = c.strokeStyle ? h : f;
        b.setAttribute(j, u + ":url(#" + m + ")")
    },
    t = function (d, e, g, l, n) {
        if (g.gradient ? s(d, e, g, l, n) : (r(d), e.setAttribute(j, "")), e.setAttribute(f, g.fillStyle ? jsPlumbUtil.convertStyle(g.fillStyle, !0) : k), e.setAttribute(h, g.strokeStyle ? jsPlumbUtil.convertStyle(g.strokeStyle, !0) : k), g.lineWidth && e.setAttribute(i, g.lineWidth), g[c] && g[m] && !g[b]) {
            var o = -1 == g[c].indexOf(",") ? " " : ",",
            p = g[c].split(o),
            q = "";
            p.forEach(function (a) {
                q += Math.floor(a * g.lineWidth) + o
            }),
            e.setAttribute(b, q)
        } else g[b] && e.setAttribute(b, g[b]);
        for (var t in a) g[t] && e.setAttribute(a[t], g[t])
    },
    u = function (a, b, c) {
        for (var d = c.split(" "), e = a.className, f = e.baseVal.split(" "), g = 0; g < d.length; g++) if (b) -1 == f.indexOf(d[g]) && f.push(d[g]);
        else {
            var h = f.indexOf(d[g]); -1 != h && f.splice(h, 1)
        }
        a.className.baseVal = f.join(" ")
    },
    v = function (a, b) {
        u(a, !0, b)
    },
    w = function (a, b) {
        u(a, !1, b)
    },
    x = function (a, b, c) {
        a.childNodes.length > c ? a.insertBefore(b, a.childNodes[c]) : a.appendChild(b)
    };
    jsPlumbUtil.svg = {
        addClass: v,
        removeClass: w,
        node: p,
        attr: o,
        pos: q
    };
    var y = function (a) {
        var b = a.pointerEventsSpec || "all",
        c = {};
        jsPlumb.jsPlumbUIComponent.apply(this, a.originalArgs),
        this.canvas = null,
        this.path = null,
        this.svg = null;
        var d = a.cssClass + " " + (a.originalArgs[0].cssClass || ""),
        e = {
            style: "",
            width: 0,
            height: 0,
            "pointer-events": b,
            position: "absolute"
        };
        this.svg = p("svg", e),
        a.useDivWrapper ? (this.canvas = document.createElement("div"), this.canvas.style.position = "absolute", jsPlumbUtil.sizeElement(this.canvas, 0, 0, 1, 1), this.canvas.className = d) : (o(this.svg, {
            "class": d
        }), this.canvas = this.svg),
        a._jsPlumb.appendElement(this.canvas, a.originalArgs[0].parent),
        a.useDivWrapper && this.canvas.appendChild(this.svg);
        var f = [this.canvas];
        return this.getDisplayElements = function () {
            return f
        },
        this.appendDisplayElement = function (a) {
            f.push(a)
        },
        this.paint = function (b, d, e) {
            if (null != b) {
                var f, g = [this.x, this.y],
                h = [this.w, this.h];
                null != e && (e.xmin < 0 && (g[0] += e.xmin), e.ymin < 0 && (g[1] += e.ymin), h[0] = e.xmax + (e.xmin < 0 ? -e.xmin : 0), h[1] = e.ymax + (e.ymin < 0 ? -e.ymin : 0)),
                a.useDivWrapper ? (jsPlumbUtil.sizeElement(this.canvas, g[0], g[1], h[0], h[1]), g[0] = 0, g[1] = 0, f = q([0, 0])) : f = q([g[0], g[1]]),
                c.paint.apply(this, arguments),
                o(this.svg, {
                    style: f,
                    width: h[0],
                    height: h[1]
                })
            }
        },
        {
            renderer: c
        }
    };
    jsPlumbUtil.extend(y, jsPlumb.jsPlumbUIComponent, {
        cleanup: function () {
            jsPlumbUtil.removeElement(this.canvas),
            this.svg = null,
            this.canvas = null,
            this.path = null
        }
    }),
    jsPlumb.ConnectorRenderers.svg = function (a) {
        var b = this,
        c = y.apply(this, [{
            cssClass: a._jsPlumb.connectorClass,
            originalArgs: arguments,
            pointerEventsSpec: "none",
            _jsPlumb: a._jsPlumb
        }]);
        c.renderer.paint = function (c, d, e) {
            var f = b.getSegments(),
            g = "",
            h = [0, 0];
            e.xmin < 0 && (h[0] = -e.xmin),
            e.ymin < 0 && (h[1] = -e.ymin);
            for (var i = 0; i < f.length; i++) g += jsPlumb.Segments.svg.SegmentRenderer.getPath(f[i]),
            g += " ";
            var j = {
                d: g,
                transform: "translate(" + h[0] + "," + h[1] + ")",
                "pointer-events": a["pointer-events"] || "visibleStroke"
            },
            k = null,
            l = [b.x, b.y, b.w, b.h];
            if (c.outlineColor) {
                var m = c.outlineWidth || 1,
                n = c.lineWidth + 2 * m;
                k = jsPlumb.CurrentLibrary.extend({},
                c),
                k.strokeStyle = jsPlumbUtil.convertStyle(c.outlineColor),
                k.lineWidth = n,
                null == b.bgPath ? (b.bgPath = p("path", j), x(b.svg, b.bgPath, 0), b.attachListeners(b.bgPath, b)) : o(b.bgPath, j),
                t(b.svg, b.bgPath, k, l, b)
            }
            null == b.path ? (b.path = p("path", j), x(b.svg, b.path, c.outlineColor ? 1 : 0), b.attachListeners(b.path, b)) : o(b.path, j),
            t(b.svg, b.path, c, l, b)
        },
        this.reattachListeners = function () {
            this.bgPath && this.reattachListenersForElement(this.bgPath, this),
            this.path && this.reattachListenersForElement(this.path, this)
        }
    },
    jsPlumbUtil.extend(jsPlumb.ConnectorRenderers.svg, y),
    jsPlumb.Segments.svg = {
        SegmentRenderer: {
            getPath: function (a) {
                return {
                    Straight: function () {
                        var b = a.getCoordinates();
                        return "M " + b.x1 + " " + b.y1 + " L " + b.x2 + " " + b.y2
                    },
                    Bezier: function () {
                        var b = a.params;
                        return "M " + b.x1 + " " + b.y1 + " C " + b.cp1x + " " + b.cp1y + " " + b.cp2x + " " + b.cp2y + " " + b.x2 + " " + b.y2
                    },
                    Arc: function () {
                        var b = a.params,
                        c = a.sweep > Math.PI ? 1 : 0,
                        d = a.anticlockwise ? 0 : 1;
                        return "M" + a.x1 + " " + a.y1 + " A " + a.radius + " " + b.r + " 0 " + c + "," + d + " " + a.x2 + " " + a.y2
                    }
                }[a.type]()
            }
        }
    };
    var z = window.SvgEndpoint = function (a) {
        var b = y.apply(this, [{
            cssClass: a._jsPlumb.endpointClass,
            originalArgs: arguments,
            pointerEventsSpec: "all",
            useDivWrapper: !0,
            _jsPlumb: a._jsPlumb
        }]);
        b.renderer.paint = function (a) {
            var b = jsPlumb.extend({},
            a);
            b.outlineColor && (b.strokeWidth = b.outlineWidth, b.strokeStyle = jsPlumbUtil.convertStyle(b.outlineColor, !0)),
            null == this.node ? (this.node = this.makeNode(b), this.svg.appendChild(this.node), this.attachListeners(this.node, this)) : null != this.updateNode && this.updateNode(this.node),
            t(this.svg, this.node, b, [this.x, this.y, this.w, this.h], this),
            q(this.node, [this.x, this.y])
        }.bind(this)
    };
    jsPlumbUtil.extend(z, y, {
        reattachListeners: function () {
            this.node && this.reattachListenersForElement(this.node, this)
        }
    }),
    jsPlumb.Endpoints.svg.Dot = function () {
        jsPlumb.Endpoints.Dot.apply(this, arguments),
        z.apply(this, arguments),
        this.makeNode = function () {
            return p("circle", {
                cx: this.w / 2,
                cy: this.h / 2,
                r: this.radius
            })
        },
        this.updateNode = function (a) {
            o(a, {
                cx: this.w / 2,
                cy: this.h / 2,
                r: this.radius
            })
        }
    },
    jsPlumbUtil.extend(jsPlumb.Endpoints.svg.Dot, [jsPlumb.Endpoints.Dot, z]),
    jsPlumb.Endpoints.svg.Rectangle = function () {
        jsPlumb.Endpoints.Rectangle.apply(this, arguments),
        z.apply(this, arguments),
        this.makeNode = function () {
            return p("rect", {
                width: this.w,
                height: this.h
            })
        },
        this.updateNode = function (a) {
            o(a, {
                width: this.w,
                height: this.h
            })
        }
    },
    jsPlumbUtil.extend(jsPlumb.Endpoints.svg.Rectangle, [jsPlumb.Endpoints.Rectangle, z]),
    jsPlumb.Endpoints.svg.Image = jsPlumb.Endpoints.Image,
    jsPlumb.Endpoints.svg.Blank = jsPlumb.Endpoints.Blank,
    jsPlumb.Overlays.svg.Label = jsPlumb.Overlays.Label,
    jsPlumb.Overlays.svg.Custom = jsPlumb.Overlays.Custom;
    var A = function (a, b) {
        a.apply(this, b),
        jsPlumb.jsPlumbUIComponent.apply(this, b),
        this.isAppendedAtTopLevel = !1,
        this.path = null,
        this.paint = function (a, d) {
            if (a.component.svg && d) {
                null == this.path && (this.path = p("path", {
                    "pointer-events": "all"
                }), a.component.svg.appendChild(this.path), this.attachListeners(this.path, a.component), this.attachListeners(this.path, this));
                var e = b && 1 == b.length ? b[0].cssClass || "" : "",
                f = [0, 0];
                d.xmin < 0 && (f[0] = -d.xmin),
                d.ymin < 0 && (f[1] = -d.ymin),
                o(this.path, {
                    d: c(a.d),
                    "class": e,
                    stroke: a.strokeStyle ? a.strokeStyle : null,
                    fill: a.fillStyle ? a.fillStyle : null,
                    transform: "translate(" + f[0] + "," + f[1] + ")"
                })
            }
        };
        var c = function (a) {
            return "M" + a.hxy.x + "," + a.hxy.y + " L" + a.tail[0].x + "," + a.tail[0].y + " L" + a.cxy.x + "," + a.cxy.y + " L" + a.tail[1].x + "," + a.tail[1].y + " L" + a.hxy.x + "," + a.hxy.y
        };
        this.reattachListeners = function () {
            this.path && this.reattachListenersForElement(this.path, this)
        }
    };
    jsPlumbUtil.extend(A, jsPlumb.jsPlumbUIComponent, {
        cleanup: function () {
            null != this.path && jsPlumb.CurrentLibrary.removeElement(this.path)
        }
    }),
    jsPlumb.Overlays.svg.Arrow = function () {
        A.apply(this, [jsPlumb.Overlays.Arrow, arguments])
    },
    jsPlumbUtil.extend(jsPlumb.Overlays.svg.Arrow, [jsPlumb.Overlays.Arrow, A]),
    jsPlumb.Overlays.svg.PlainArrow = function () {
        A.apply(this, [jsPlumb.Overlays.PlainArrow, arguments])
    },
    jsPlumbUtil.extend(jsPlumb.Overlays.svg.PlainArrow, [jsPlumb.Overlays.PlainArrow, A]),
    jsPlumb.Overlays.svg.Diamond = function () {
        A.apply(this, [jsPlumb.Overlays.Diamond, arguments])
    },
    jsPlumbUtil.extend(jsPlumb.Overlays.svg.Diamond, [jsPlumb.Overlays.Diamond, A]),
    jsPlumb.Overlays.svg.GuideLines = function () {
        var a, b, c = null,
        d = this;
        jsPlumb.Overlays.GuideLines.apply(this, arguments),
        this.paint = function (f, g) {
            null == c && (c = p("path"), f.connector.svg.appendChild(c), d.attachListeners(c, f.connector), d.attachListeners(c, d), a = p("path"), f.connector.svg.appendChild(a), d.attachListeners(a, f.connector), d.attachListeners(a, d), b = p("path"), f.connector.svg.appendChild(b), d.attachListeners(b, f.connector), d.attachListeners(b, d));
            var h = [0, 0];
            g.xmin < 0 && (h[0] = -g.xmin),
            g.ymin < 0 && (h[1] = -g.ymin),
            o(c, {
                d: e(f.head, f.tail),
                stroke: "red",
                fill: null,
                transform: "translate(" + h[0] + "," + h[1] + ")"
            }),
            o(a, {
                d: e(f.tailLine[0], f.tailLine[1]),
                stroke: "blue",
                fill: null,
                transform: "translate(" + h[0] + "," + h[1] + ")"
            }),
            o(b, {
                d: e(f.headLine[0], f.headLine[1]),
                stroke: "green",
                fill: null,
                transform: "translate(" + h[0] + "," + h[1] + ")"
            })
        };
        var e = function (a, b) {
            return "M " + a.x + "," + a.y + " L" + b.x + "," + b.y
        }
    },
    jsPlumbUtil.extend(jsPlumb.Overlays.svg.GuideLines, jsPlumb.Overlays.GuideLines)
}(),
function () {
    var a = {
        "stroke-linejoin": "joinstyle",
        joinstyle: "joinstyle",
        endcap: "endcap",
        miterlimit: "miterlimit"
    },
    b = null;
    if (document.createStyleSheet && document.namespaces) {
        var c = [".jsplumb_vml", "jsplumb\\:textbox", "jsplumb\\:oval", "jsplumb\\:rect", "jsplumb\\:stroke", "jsplumb\\:shape", "jsplumb\\:group"],
        d = "behavior:url(#default#VML);position:absolute;";
        b = document.createStyleSheet();
        for (var e = 0; e < c.length; e++) b.addRule(c[e], d);
        document.namespaces.add("jsplumb", "urn:schemas-microsoft-com:vml")
    }
    jsPlumb.vml = {};
    var f = 1e3,
    g = function (a, b) {
        for (var c in b) a[c] = b[c]
    },
    h = function (a, b, c, d, e, f) {
        c = c || {};
        var h = document.createElement("jsplumb:" + a);
        return f ? e.appendElement(h, d) : jsPlumb.CurrentLibrary.appendElement(h, d),
        h.className = (c["class"] ? c["class"] + " " : "") + "jsplumb_vml",
        i(h, b),
        g(h, c),
        h
    },
    i = function (a, b, c) {
        a.style.left = b[0] + "px",
        a.style.top = b[1] + "px",
        a.style.width = b[2] + "px",
        a.style.height = b[3] + "px",
        a.style.position = "absolute",
        c && (a.style.zIndex = c)
    },
    j = jsPlumb.vml.convertValue = function (a) {
        return Math.floor(a * f)
    },
    k = function (a, b, c, d) {
        "transparent" === b ? d.setOpacity(c, "0.0") : d.setOpacity(c, "1.0")
    },
    l = function (a, b, c, d) {
        var e = {};
        if (b.strokeStyle) {
            e.stroked = "true";
            var f = jsPlumbUtil.convertStyle(b.strokeStyle, !0);
            e.strokecolor = f,
            k(e, f, "stroke", c),
            e.strokeweight = b.lineWidth + "px"
        } else e.stroked = "false";
        if (b.fillStyle) {
            e.filled = "true";
            var i = jsPlumbUtil.convertStyle(b.fillStyle, !0);
            e.fillcolor = i,
            k(e, i, "fill", c)
        } else e.filled = "false";
        if (b.dashstyle) null == c.strokeNode ? c.strokeNode = h("stroke", [0, 0, 0, 0], {
            dashstyle: b.dashstyle
        },
        a, d) : c.strokeNode.dashstyle = b.dashstyle;
        else if (b["stroke-dasharray"] && b.lineWidth) {
            for (var j = -1 == b["stroke-dasharray"].indexOf(",") ? " " : ",", l = b["stroke-dasharray"].split(j), m = "", n = 0; n < l.length; n++) m += Math.floor(l[n] / b.lineWidth) + j;
            null == c.strokeNode ? c.strokeNode = h("stroke", [0, 0, 0, 0], {
                dashstyle: m
            },
            a, d) : c.strokeNode.dashstyle = m
        }
        g(a, e)
    },
    m = function () {
        var a = this;
        jsPlumb.jsPlumbUIComponent.apply(this, arguments),
        this.opacityNodes = {
            stroke: null,
            fill: null
        },
        this.initOpacityNodes = function (b) {
            a.opacityNodes.stroke = h("stroke", [0, 0, 1, 1], {
                opacity: "0.0"
            },
            b, a._jsPlumb.instance),
            a.opacityNodes.fill = h("fill", [0, 0, 1, 1], {
                opacity: "0.0"
            },
            b, a._jsPlumb.instance)
        },
        this.setOpacity = function (b, c) {
            var d = a.opacityNodes[b];
            d && (d.opacity = "" + c)
        };
        var b = [];
        this.getDisplayElements = function () {
            return b
        },
        this.appendDisplayElement = function (c, d) {
            d || a.canvas.parentNode.appendChild(c),
            b.push(c)
        }
    };
    jsPlumbUtil.extend(m, jsPlumb.jsPlumbUIComponent, {
        cleanup: function () {
            this.bgCanvas && jsPlumbUtil.removeElement(this.bgCanvas),
            jsPlumbUtil.removeElement(this.canvas)
        }
    });
    var n = jsPlumb.ConnectorRenderers.vml = function (b) {
        this.strokeNode = null,
        this.canvas = null,
        m.apply(this, arguments);
        var c = this._jsPlumb.instance.connectorClass + (b.cssClass ? " " + b.cssClass : "");
        this.paint = function (d) {
            if (null !== d) {
                this.w = Math.max(this.w, 1),
                this.h = Math.max(this.h, 1);
                for (var e = this.getSegments(), j = {
                    path: ""
                },
                k = [this.x, this.y, this.w, this.h], m = 0; m < e.length; m++) j.path += jsPlumb.Segments.vml.SegmentRenderer.getPath(e[m]),
                j.path += " ";
                if (d.outlineColor) {
                    var n = d.outlineWidth || 1,
                    o = d.lineWidth + 2 * n,
                    p = {
                        strokeStyle: jsPlumbUtil.convertStyle(d.outlineColor),
                        lineWidth: o
                    };
                    for (var q in a) p[q] = d[q];
                    null == this.bgCanvas ? (j["class"] = c, j.coordsize = k[2] * f + "," + k[3] * f, this.bgCanvas = h("shape", k, j, b.parent, this._jsPlumb.instance, !0), i(this.bgCanvas, k), this.appendDisplayElement(this.bgCanvas, !0), this.attachListeners(this.bgCanvas, this), this.initOpacityNodes(this.bgCanvas, ["stroke"])) : (j.coordsize = k[2] * f + "," + k[3] * f, i(this.bgCanvas, k), g(this.bgCanvas, j)),
                    l(this.bgCanvas, p, this)
                }
                null == this.canvas ? (j["class"] = c, j.coordsize = k[2] * f + "," + k[3] * f, this.canvas = h("shape", k, j, b.parent, this._jsPlumb.instance, !0), this.appendDisplayElement(this.canvas, !0), this.attachListeners(this.canvas, this), this.initOpacityNodes(this.canvas, ["stroke"])) : (j.coordsize = k[2] * f + "," + k[3] * f, i(this.canvas, k), g(this.canvas, j)),
                l(this.canvas, d, this, this._jsPlumb.instance)
            }
        }
    };
    jsPlumbUtil.extend(n, m, {
        reattachListeners: function () {
            this.canvas && this.reattachListenersForElement(this.canvas, this)
        }
    });
    var o = window.VmlEndpoint = function (a) {
        m.apply(this, arguments),
        this._jsPlumb.vml = null,
        this.canvas = document.createElement("div"),
        this.canvas.style.position = "absolute",
        this._jsPlumb.clazz = this._jsPlumb.instance.endpointClass + (a.cssClass ? " " + a.cssClass : ""),
        a._jsPlumb.appendElement(this.canvas, a.parent),
        this.paint = function (a, b) {
            var c = {},
            d = this._jsPlumb.vml;
            jsPlumbUtil.sizeElement(this.canvas, this.x, this.y, this.w, this.h),
            null == this._jsPlumb.vml ? (c["class"] = this._jsPlumb.clazz, d = this._jsPlumb.vml = this.getVml([0, 0, this.w, this.h], c, b, this.canvas, this._jsPlumb.instance), this.attachListeners(d, this), this.appendDisplayElement(d, !0), this.appendDisplayElement(this.canvas, !0), this.initOpacityNodes(d, ["fill"])) : (i(d, [0, 0, this.w, this.h]), g(d, c)),
            l(d, a, this)
        }
    };
    jsPlumbUtil.extend(o, m, {
        reattachListeners: function () {
            this._jsPlumb.vml && this.reattachListenersForElement(this._jsPlumb.vml, this)
        }
    }),
    jsPlumb.Segments.vml = {
        SegmentRenderer: {
            getPath: function (a) {
                return {
                    Straight: function (a) {
                        var b = a.params;
                        return "m" + j(b.x1) + "," + j(b.y1) + " l" + j(b.x2) + "," + j(b.y2) + " e"
                    },
                    Bezier: function (a) {
                        var b = a.params;
                        return "m" + j(b.x1) + "," + j(b.y1) + " c" + j(b.cp1x) + "," + j(b.cp1y) + "," + j(b.cp2x) + "," + j(b.cp2y) + "," + j(b.x2) + "," + j(b.y2) + " e"
                    },
                    Arc: function (a) {
                        var b = a.params,
                        c = Math.min(b.x1, b.x2),
                        d = (Math.max(b.x1, b.x2), Math.min(b.y1, b.y2)),
                        e = (Math.max(b.y1, b.y2), a.anticlockwise ? 1 : 0),
                        f = a.anticlockwise ? "at " : "wa ",
                        g = function () {
                            var f = [null, [function () {
                                return [c, d]
                            },
                            function () {
                                return [c - b.r, d - b.r]
                            }], [function () {
                                return [c - b.r, d]
                            },
                            function () {
                                return [c, d - b.r]
                            }], [function () {
                                return [c - b.r, d - b.r]
                            },
                            function () {
                                return [c, d]
                            }], [function () {
                                return [c, d - b.r]
                            },
                            function () {
                                return [c - b.r, d]
                            }]][a.segment][e]();
                            return j(f[0]) + "," + j(f[1]) + "," + j(f[0] + 2 * b.r) + "," + j(f[1] + 2 * b.r)
                        };
                        return f + g() + "," + j(b.x1) + "," + j(b.y1) + "," + j(b.x2) + "," + j(b.y2) + " e"
                    }
                }[a.type](a)
            }
        }
    },
    jsPlumb.Endpoints.vml.Dot = function () {
        jsPlumb.Endpoints.Dot.apply(this, arguments),
        o.apply(this, arguments),
        this.getVml = function (a, b, c, d, e) {
            return h("oval", a, b, d, e)
        }
    },
    jsPlumbUtil.extend(jsPlumb.Endpoints.vml.Dot, o),
    jsPlumb.Endpoints.vml.Rectangle = function () {
        jsPlumb.Endpoints.Rectangle.apply(this, arguments),
        o.apply(this, arguments),
        this.getVml = function (a, b, c, d, e) {
            return h("rect", a, b, d, e)
        }
    },
    jsPlumbUtil.extend(jsPlumb.Endpoints.vml.Rectangle, o),
    jsPlumb.Endpoints.vml.Image = jsPlumb.Endpoints.Image,
    jsPlumb.Endpoints.vml.Blank = jsPlumb.Endpoints.Blank,
    jsPlumb.Overlays.vml.Label = jsPlumb.Overlays.Label,
    jsPlumb.Overlays.vml.Custom = jsPlumb.Overlays.Custom;
    var p = function (a, b) {
        a.apply(this, b),
        m.apply(this, b);
        var c = this;
        c.canvas = null,
        c.isAppendedAtTopLevel = !0;
        var d = function (a) {
            return "m " + j(a.hxy.x) + "," + j(a.hxy.y) + " l " + j(a.tail[0].x) + "," + j(a.tail[0].y) + " " + j(a.cxy.x) + "," + j(a.cxy.y) + " " + j(a.tail[1].x) + "," + j(a.tail[1].y) + " x e"
        };
        this.paint = function (a) {
            var e = {},
            j = a.d,
            k = a.component;
            a.strokeStyle && (e.stroked = "true", e.strokecolor = jsPlumbUtil.convertStyle(a.strokeStyle, !0)),
            a.lineWidth && (e.strokeweight = a.lineWidth + "px"),
            a.fillStyle && (e.filled = "true", e.fillcolor = a.fillStyle);
            var l = Math.min(j.hxy.x, j.tail[0].x, j.tail[1].x, j.cxy.x),
            m = Math.min(j.hxy.y, j.tail[0].y, j.tail[1].y, j.cxy.y),
            n = Math.max(j.hxy.x, j.tail[0].x, j.tail[1].x, j.cxy.x),
            o = Math.max(j.hxy.y, j.tail[0].y, j.tail[1].y, j.cxy.y),
            p = Math.abs(n - l),
            q = Math.abs(o - m),
            r = [l, m, p, q];
            if (e.path = d(j), e.coordsize = k.w * f + "," + k.h * f, r[0] = k.x, r[1] = k.y, r[2] = k.w, r[3] = k.h, null == c.canvas) {
                var s = k._jsPlumb.overlayClass || "",
                t = b && 1 == b.length ? b[0].cssClass || "" : "";
                e["class"] = t + " " + s,
                c.canvas = h("shape", r, e, k.canvas.parentNode, k._jsPlumb.instance, !0),
                k.appendDisplayElement(c.canvas, !0),
                c.attachListeners(c.canvas, k),
                c.attachListeners(c.canvas, c)
            } else i(c.canvas, r),
            g(c.canvas, e)
        },
        this.reattachListeners = function () {
            c.canvas && c.reattachListenersForElement(c.canvas, c)
        },
        this.cleanup = function () {
            null != c.canvas && jsPlumb.CurrentLibrary.removeElement(c.canvas)
        }
    };
    jsPlumbUtil.extend(p, m),
    jsPlumb.Overlays.vml.Arrow = function () {
        p.apply(this, [jsPlumb.Overlays.Arrow, arguments])
    },
    jsPlumbUtil.extend(jsPlumb.Overlays.vml.Arrow, [jsPlumb.Overlays.Arrow, p]),
    jsPlumb.Overlays.vml.PlainArrow = function () {
        p.apply(this, [jsPlumb.Overlays.PlainArrow, arguments])
    },
    jsPlumbUtil.extend(jsPlumb.Overlays.vml.PlainArrow, [jsPlumb.Overlays.PlainArrow, p]),
    jsPlumb.Overlays.vml.Diamond = function () {
        p.apply(this, [jsPlumb.Overlays.Diamond, arguments])
    },
    jsPlumbUtil.extend(jsPlumb.Overlays.vml.Diamond, [jsPlumb.Overlays.Diamond, p])
}(),
function (a) {
    var b = function (b) {
        return "string" == typeof b ? a("#" + b) : a(b)
    };
    jsPlumb.CurrentLibrary = {
        addClass: function (a, c) {
            a = b(a);
            try {
                a[0].className.constructor == SVGAnimatedString && jsPlumbUtil.svg.addClass(a[0], c)
            } catch (d) { }
            try {
                a.addClass(c)
            } catch (d) { }
        },
        animate: function (a, b, c) {
            a.animate(b, c)
        },
        appendElement: function (a, c) {
            b(c).append(a)
        },
        ajax: function (b) {
            b = b || {},
            b.type = b.type || "get",
            a.ajax(b)
        },
        bind: function (a, c, d) {
            a = b(a),
            a.bind(c, d)
        },
        destroyDraggable: function (b) {
            a(b).data("draggable") && a(b).draggable("destroy")
        },
        destroyDroppable: function (b) {
            a(b).data("droppable") && a(b).droppable("destroy")
        },
        dragEvents: {
            start: "start",
            stop: "stop",
            drag: "drag",
            step: "step",
            over: "over",
            out: "out",
            drop: "drop",
            complete: "complete"
        },
        extend: function (b, c) {
            return a.extend(b, c)
        },
        getClientXY: function (a) {
            return [a.clientX, a.clientY]
        },
        getDragObject: function (a) {
            return a[1].draggable || a[1].helper
        },
        getDragScope: function (b) {
            return a(b).draggable("option", "scope")
        },
        getDropEvent: function (a) {
            return a[0]
        },
        getDropScope: function (b) {
            return a(b).droppable("option", "scope")
        },
        getDOMElement: function (a) {
            return null == a ? null : "string" == typeof a ? document.getElementById(a) : a.context || null != a.length ? a[0] : a
        },
        getElementObject: b,
        getOffset: function (a) {
            return a.offset()
        },
        getOriginalEvent: function (a) {
            return a.originalEvent
        },
        getPageXY: function (a) {
            return [a.pageX, a.pageY]
        },
        getParent: function (a) {
            return b(a).parent()
        },
        getScrollLeft: function (a) {
            return a.scrollLeft()
        },
        getScrollTop: function (a) {
            return a.scrollTop()
        },
        getSelector: function (c, d) {
            return 2 == arguments.length ? b(c).find(d) : a(c)
        },
        getSize: function (b) {
            return b = a(b),
            [b.outerWidth(), b.outerHeight()]
        },
        getTagName: function (a) {
            var c = b(a);
            return c.length > 0 ? c[0].tagName : null
        },
        getUIPosition: function (a, b) {
            if (b = b || 1, 1 == a.length) ret = {
                left: a[0].pageX,
                top: a[0].pageY
            };
            else {
                var c = a[1],
                d = c.offset;
                ret = d || c.absolutePosition,
                c.position.left /= b,
                c.position.top /= b
            }
            return {
                left: ret.left / b,
                top: ret.top / b
            }
        },
        hasClass: function (a, b) {
            return a.hasClass(b)
        },
        initDraggable: function (b, c, d, e) {
            c = c || {},
            b = a(b),
            c.start = jsPlumbUtil.wrap(c.start,
            function () {
                a("body").addClass(e.dragSelectClass)
            },
            !1),
            c.stop = jsPlumbUtil.wrap(c.stop,
            function () {
                a("body").removeClass(e.dragSelectClass)
            }),
            c.doNotRemoveHelper || (c.helper = null),
            d && (c.scope = c.scope || jsPlumb.Defaults.Scope),
            b.draggable(c)
        },
        initDroppable: function (b, c) {
            c.scope = c.scope || jsPlumb.Defaults.Scope,
            a(b).droppable(c)
        },
        isAlreadyDraggable: function (b) {
            return a(b).hasClass("ui-draggable")
        },
        isDragSupported: function (b) {
            return a(b).draggable
        },
        isDropSupported: function (b) {
            return a(b).droppable
        },
        removeClass: function (a, c) {
            a = b(a);
            try {
                if (a[0].className.constructor == SVGAnimatedString) return jsPlumbUtil.svg.removeClass(a[0], c),
                void 0
            } catch (d) { }
            a.removeClass(c)
        },
        removeElement: function (a) {
            b(a).remove()
        },
        setDragFilter: function (a, b) {
            jsPlumb.CurrentLibrary.isAlreadyDraggable(a) && a.draggable("option", "cancel", b)
        },
        setDraggable: function (a, b) {
            a.draggable("option", "disabled", !b)
        },
        setDragScope: function (a, b) {
            a.draggable("option", "scope", b)
        },
        setOffset: function (a, c) {
            b(a).offset(c)
        },
        trigger: function (a, c, d) {
            var e = jQuery._data(b(a)[0], "handle");
            e(d)
        },
        unbind: function (a, c, d) {
            a = b(a),
            a.unbind(c, d)
        }
    },
    a(document).ready(jsPlumb.init)
}(jQuery);