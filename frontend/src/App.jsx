import { useState } from 'react'
import UseEarthquakes from './hooks/useEarthquakes'

function App() {
    const {
        earthquakes,
        loading,
        error,
        fetchAndStoreEarthquakes,
        filterByMagnitude,
        filterByTime,
        deleteById
    } = UseEarthquakes();

    const [minMag, setMinMag] = useState(2.5)

    const handleMagChange = (e) => {
        const val = e.target.value;
        setMinMag(val);
        filterByMagnitude(val);
    }

    const handleTimeChange = (e) => {
        const dateValue = e.target.value;
        if (dateValue) {
            const timestamp = new Date(dateValue).getTime();
            filterByTime(timestamp);
        }
    }

    const formatDate = (timestamp) => {
        if (!timestamp) return 'N/A'
        return new Date(timestamp).toLocaleString()
    }

    const getMagClass = (mag) => {
        if (!mag && mag !== 0) return 'bg-secondary'
        if (mag >= 7) return 'bg-danger'
        if (mag >= 5.5) return 'bg-danger bg-opacity-75'
        if (mag >= 4.5) return 'bg-warning text-dark'
        if (mag >= 3.5) return 'bg-secondary'
        return 'bg-secondary bg-opacity-50'
    }

    return (
        <div className="container py-5">
            <header className="text-center mb-5 position-relative">
                <h1 className="display-4 fw-bold">Earthquake Tracker</h1>
                <p className="lead text-muted">Monitor recent seismic activity around the world</p>
                <button
                    className="btn btn-primary shadow-sm position-absolute top-0 end-0 mt-3 d-flex align-items-center gap-2"
                    onClick={fetchAndStoreEarthquakes}
                    disabled={loading}
                >
                    {loading && (
                        <span className="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                    )}
                    {loading ? 'Syncing...' : 'Sync Latest Data'}
                </button>
            </header>

            <section className="card shadow-sm mb-4">
                <div className="card-body">
                    <div className="row g-3">
                        <div className="col-md-6">
                            <label htmlFor="minMag" className="form-label fw-bold">
                                Min Magnitude: <span className="text-primary">{minMag}</span>
                            </label>
                            <input
                                type="range"
                                className="form-range"
                                id="minMag"
                                min="0"
                                max="9"
                                step="0.1"
                                value={minMag}
                                onChange={handleMagChange}
                            />
                        </div>

                        <div className="col-md-6">
                            <label htmlFor="timeFilter" className="form-label fw-bold">Filter from Date:</label>
                            <input
                                type="date"
                                className="form-control"
                                id="timeFilter"
                                onChange={handleTimeChange}
                            />
                        </div>
                    </div>
                </div>
            </section>

            <main>
                {loading && (
                    <div className="text-center py-5">
                        <div className="spinner-border text-primary" role="status">
                            <span className="visually-hidden">Loading...</span>
                        </div>
                        <p className="mt-3">Processing earthquake data...</p>
                    </div>
                )}

                {error && (
                    <div className="alert alert-danger shadow-sm text-center" role="alert">
                        {error}
                    </div>
                )}

                {!loading && !error && (
                    <div className="table-responsive shadow-sm rounded border">
                        <table className="table table-striped table-hover align-middle mb-0">
                            <thead className="table-dark">
                            <tr>
                                <th scope="col" style={{ width: '100px' }}>Magnitude</th>
                                <th scope="col">Place</th>
                                <th scope="col" style={{ width: '220px' }}>Time</th>
                                <th scope="col">Mag Type</th>
                                <th scope="col" style={{ width: '150px' }} className="text-center">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            {earthquakes && earthquakes.length > 0 ? (
                                earthquakes.map((eq) => {
                                    const data = eq.properties || eq;
                                    const mag = typeof data.magnitude === 'number' ? data.magnitude : data.magnitude;
                                    return (
                                        <tr key={eq.id}>
                                            <td>
                                                    <span className={`badge rounded-pill ${getMagClass(mag)}`} style={{ minWidth: '40px' }}>
                                                        {typeof mag === 'number' ? mag.toFixed(1) : 'N/A'}
                                                    </span>
                                            </td>
                                            <td>
                                                <div className="fw-medium text-break">{data.place || 'Unknown Location'}</div>
                                                {data.title && <small className="text-muted">{data.title}</small>}
                                            </td>
                                            <td className="text-nowrap">{formatDate(data.time)}</td>
                                            <td className="text-capitalize">{data.magType || '—'}</td>
                                            <td className="text-center">
                                                <div className="d-flex gap-2 justify-content-center">
                                                    {data.url && (
                                                        <a
                                                            href={data.url}
                                                            target="_blank"
                                                            rel="noopener noreferrer"
                                                            className="btn btn-sm btn-outline-primary"
                                                            title="View USGS Details"
                                                        >
                                                            Details
                                                        </a>
                                                    )}
                                                    <button
                                                        className="btn btn-sm btn-outline-danger"
                                                        onClick={() => deleteById(eq.id)}
                                                        title="Delete Record"
                                                    >
                                                        🗑️
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>
                                    )
                                })
                            ) : (
                                <tr>
                                    <td colSpan="5" className="text-center py-5 text-muted">
                                        <p className="mb-0 fs-5">No earthquakes found. Try syncing or adjusting filters.</p>
                                    </td>
                                </tr>
                            )}
                            </tbody>
                        </table>
                    </div>
                )}
            </main>
        </div>
    )
}

export default App