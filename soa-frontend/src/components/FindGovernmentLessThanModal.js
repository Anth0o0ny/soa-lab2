const FindGovernmentLessThanModal = ({ isOpen, onClose, cities }) => {
    if (!isOpen) return null;

    return (
        <div className="modal">
            <div className="modal-content">
                <h3>Cities with Government Less Than</h3>
                {cities.length > 0 ? (
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Coordinates (X, Y)</th>
                                <th>Area</th>
                                <th>Population</th>
                                <th>Meters Above Sea Level</th>
                                <th>Climate</th>
                                <th>Government</th>
                                <th>Standard of Living</th>
                                <th>Governor Age</th>
                            </tr>
                        </thead>
                        <tbody>
                            {cities.map((city) => (
                                <tr key={city.id}>
                                    <td>{city.id}</td>
                                    <td>{city.name}</td>
                                    <td>({city.coordinates.x}, {city.coordinates.y})</td>
                                    <td>{city.area}</td>
                                    <td>{city.population}</td>
                                    <td>{city.metersAboveSeaLevel}</td>
                                    <td>{city.climate}</td>
                                    <td>{city.government}</td>
                                    <td>{city.standardOfLiving}</td>
                                    <td>{city.governor ? city.governor.age : 'N/A'}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <p>No cities found.</p>
                )}
                <button onClick={onClose}>Close</button>
            </div>
        </div>
    );
};

export default FindGovernmentLessThanModal;